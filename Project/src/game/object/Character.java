/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game.object;
import framework.Camera;
import framework.GameObject;
import framework.Path;
import framework.TextRenderer;
import framework.World;

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
	/** The path currently being followed */
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
		super(pos, sprite, world, name, health, damage, cooldown);
		this.speed = speed;
		currentHealth = getHealth();
		currentCooldown = 0;
		path = new Path(world);
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

	protected void setCurrentCooldown(int currentCooldown) {
		this.currentCooldown = currentCooldown;
	}
	
	public boolean isTerrainBlocking() {
		return terrainBlocking;
	}

	protected void setTerrainBlocking(boolean terrainBlocking) {
		this.terrainBlocking = terrainBlocking;
	}
	
	protected Path getPath() {
		return path;
	}

	/** Set the path of the character to reach a GameObject on the map
	 * @param o The GameObject to be followed
	 */
	protected void setPath(GameObject o) {		
		setPath(o.getVectorCenter());
	}
	
	/** Set the path of the character to reach position x,y on the map
	 * @param stop Vector2f representing x,y coords of end of path
	 */
	protected void setPath(Vector2f stop) {		
		// Find a path to position x,y
		Vector2f start = new Vector2f((float)getCenterX(),(float)getCenterY());
		path.setPath(start, stop);
	}

	/** Attacks the given Character if possible
	 * @param o A character to be attacked
	 * @return Whether or not the attack was lethal
	 */
	protected boolean attack(Character o) {
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
	protected boolean takeDamage(int damage, Character attacker) {
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
	 * @param dir Number of units to move along the x, y axis
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

	/* (non-Javadoc)
	 * Render the Character's health bar
	 * @see game.framework.GameObject#render(org.newdawn.slick.Graphics, game.framework.Camera)
	 */
	@Override
	public void render(Graphics g, Camera camera) {
        if (camera.isOnScreen(this)) {
    		super.render(g, camera);
			renderHealthBar(g, camera);
        }
	}
	
	/** Render the health bar above the sprite
	 * @param g The current graphics object
	 */
	protected void renderHealthBar(Graphics g, Camera camera) {
		TextRenderer.renderText(g, getCenterX()-camera.getMinX(), getMinY()-camera.getMinY(), (float)getCurrentHealth()/getHealth(), getName());
	}
}
