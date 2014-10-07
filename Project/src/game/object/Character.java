/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game.object;
import game.framework.Camera;
import game.framework.GameObject;
import game.framework.Path;
import game.framework.TextRenderer;
import game.framework.World;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class Character extends GameObject {
	private static final long serialVersionUID = -3778466348827433489L;
	/** Max speed in pixels per millisecond */
	private float speed;
	/** Amount of health remaining */
	private int currentHealth;
	/** Time left before next possible attack */
	private int currentCooldown;
	/** The path currently being followed, if any */
	private Path path;
	/** Allows debugging removal of terrain blocking (or perhaps flying creatures) */
	private boolean terrainBlocking = true;
	
	/** Constructor for character class
	 * @param x The initial central x-coordinate
	 * @param y	The initial central y-coordinate
	 * @param sprite An Image for the character
	 * @param map The map upon which it exists
	 */
	public Character(Vector2f pos, Image sprite, World world, String name, float speed, int health, int damage, int cooldown) {
		// Super constructor takes arguments to uppermost and rightmost coords, not centre
		super(pos, sprite, world, name, health, damage, cooldown);
		this.speed = speed;
		currentHealth = getHealth();
		currentCooldown = 0;
		path = new Path();
	}
	
	/* Getters and setters */
	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getCurrentCooldown() {
		return currentCooldown;
	}

	public void setCurrentCooldown(int currentCooldown) {
		this.currentCooldown = currentCooldown;
	}
	
	public boolean isTerrainBlocking() {
		return terrainBlocking;
	}

	public void setTerrainBlocking(boolean terrainBlocking) {
		this.terrainBlocking = terrainBlocking;
	}
	
	public Path getPath() {
		return path;
	}

	/** Set the path of the character to reach a GameObject on the map
	 * @param o The GameObject to be followed
	 */
	public void setPath(GameObject o) {		
		setPath(o.getCenterX(), o.getCenterY());
	}
	
	/** Set the path of the character to reach position x,y on the map
	 * @param x Float representing mouse x-position
	 * @param y Float representing mouse y-position
	 */
	public void setPath(float x, float y) {		
		// Find a path to position x,y
		Vector2f start = new Vector2f((float)getCenterX(),(float)getCenterY());
		Vector2f stop = new Vector2f(x,y);
		Path newPath = world.findPath(start, stop);
		if (newPath.hasPath()) {
			path = newPath;
			if (newPath.length() > 1) {
				// we're already at the first node on the path, don't need to go there.
				//path.next();
			}
		}
	}

	/** Attacks the given Character if possible
	 * @param o A character to be attacked
	 * @return whether or not the attack was lethal
	 */
	public boolean attack(Character o) {
		if (getCurrentCooldown() <= 0) {
			currentCooldown = getCooldown();
			return o.takeDamage((int)(Math.random()*getDamage())+1, this);
		}
		return false;
	}
	
	/** Takes damage from an attack and checks for death
	 * @param damage The amount of damage inflicted
	 * @return Whether or not the damage was lethal
	 */
	public boolean takeDamage(int damage, Character attacker) {
		currentHealth -= damage;
		if (currentHealth <= 0) {
			destroy();
			return true;
		}		
		return false;
	}
	
	/** Move the character in any direction specified either by keyboard or predefined path.
	 * Check first that movement is not blocked by terrain
	 * Updates whether or not to use flipped sprite
	 * @param dirX Number of units to move along the x axis
	 * @param dirY Number of units to move along the y axis
	 * @param delta Number of milliseconds since last movement
	 */
	public void update(Vector2f dir, int delta) {
		if (currentCooldown > 0) {
			currentCooldown -= delta;
		}
		// Check if a current path exists
		path.update(getVectorCenter());
		if (path.hasPath()) {
			if (dir.x != 0 || dir.y != 0) {
				// Keyboard interrupts pathfinding
				path.clear();
			} else {
				// Set phantom keyboard input to reach next waypoint
				dir = path.getDir(getVectorCenter());
			}
		}
		
		// flip sprite where necessary
		if (dir.x > 0) {
			if (getSprite() == getSpriteF()) {
			setSprite(getSpriteNf());
			}
		} else if (dir.x < 0) {
			if (getSprite() == getSpriteNf()) {
				setSprite(getSpriteF());
			}
		}
		
		if (world.canMove(this,speed*dir.x*delta,0)) {
			// Movement okay in x direction
			setCenterX(getCenterX() + (float)(speed*dir.x*delta));		
		}
		if (world.canMove(this,0,speed*dir.y*delta)) {
			// Movement okay in y direction
			setCenterY(getCenterY() + (float)(speed*dir.y*delta));	
		}
	}

	@Override
	public void render(Graphics g, Camera camera) {
		super.render(g, camera);
        
        if (camera.isOnScreen(this) && !((this instanceof Player) || ((this instanceof NPC) && ((NPC) this).getSpeakTime() > 0))) {
			TextRenderer.renderText(g, getCenterX()-camera.getMinX(), getMinY()-camera.getMinY(), (float)getCurrentHealth()/getHealth(), getName());
        }
	}
}
