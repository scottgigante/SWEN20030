/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Color;
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
	private Vector2f[] path; // TODO replace with class Path
	/** The position in the path array we have reached */
	private int path_index;
	/** Allows debugging removal of terrain blocking (or perhaps flying creatures) */
	private boolean terrainBlocking = true;
	
	/** Tolerance, relative to speed, which will be accepted in a radius around the destination of a path */
	private static final double PATH_TOLERANCE = 20;
	
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
	
	/** Attacks the given Character if possible
	 * @param o A charecter to be attacked
	 */
	public void attack(Character o) {
		if (getCurrentCooldown() <= 0) {
			o.takeDamage((int)(Math.random()*getDamage()));
			currentCooldown = getCooldown();
		}
	}
	
	/** Takes damage from an attack and checks for death
	 * @param damage The amount of damage inflicted
	 */
	public void takeDamage(int damage) {
		currentHealth -= damage;
		if (currentHealth <= 0) {
			destroy();
			return;
		}		
	}
	
	/** Move the character in any direction specified either by keyboard or predefined path.
	 * Check first that movement is not blocked by terrain
	 * Updates whether or not to use flipped sprite
	 * @param dirX Number of units to move along the x axis
	 * @param dirY Number of units to move along the y axis
	 * @param delta Number of milliseconds since last movement
	 */
	public void update(float dirX, float dirY, int delta) {
		if (currentCooldown > 0) {
			currentCooldown -= delta;
		}
		// Check if a current path exists
		if (path != null) { 
			// Check if the current waypoint has been reached
			if (path[path_index].distance(new Vector2f(getCenterX(), getCenterY())) < PATH_TOLERANCE*speed) {
				if (path_index < path.length-1) {
					path_index++;
				} else {
					// Path complete!
					path = null;
				}
			}
		}
		if (path != null) {
			if (dirX != 0 || dirY != 0) {
				// Keyboard interrupts pathfinding
				path = null;
			} else {
				// Set phantom keyboard input to reach next waypoint
				// Use PATH_TOLERANCE/2 to avoid having to use sqrt(2)
				if (getCenterX() < path[path_index].x-PATH_TOLERANCE*speed/2) {
					dirX = 1;
				} else if (getCenterX() > path[path_index].x+PATH_TOLERANCE*speed/2) {
					dirX = -1;
				}
				if (getCenterY() < path[path_index].y-PATH_TOLERANCE*speed/2) {
					dirY = 1;
				} else if (getCenterY() > path[path_index].y+PATH_TOLERANCE*speed/2) {
					dirY = -1;
				}				
			}
		}
		
		// flip sprite where necessary
		if (dirX > 0) {
			if (getSprite() == getSpriteF()) {
			setSprite(getSpriteNf());
			}
		} else if (dirX < 0) {
			if (getSprite() == getSpriteNf()) {
				setSprite(getSpriteF());
			}
		}
		
		if (world.canMove(this,speed*dirX*delta,0)) {
			// Movement okay in x direction
			setCenterX(getCenterX() + (float)(speed*dirX*delta));		
		}
		if (world.canMove(this,0,speed*dirY*delta)) {
			// Movement okay in y direction
			setCenterY(getCenterY() + (float)(speed*dirY*delta));	
		}
	}

	/** Set the path of the character to reach position x,y on the map
	 * @param x Float representing mouse x-position
	 * @param y Float representing mouse y-position
	 */
	public void setPath(float x, float y) {		
		// Find a path to position x,y
		Vector2f start = new Vector2f((float)getCenterX(),(float)getCenterY());
		Vector2f stop = new Vector2f(x,y);
		Vector2f[] newPath = world.findPath(start, stop);
		if (newPath != null) {
			path = newPath;
			if (newPath.length > 1) {
				// we're already at the first node on the path, don't need to go there.
				path_index = 1;
			} else {
				path_index = 0;
			}
		}
	}

	@Override
	public void render(Graphics g, Camera camera) {
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        
        String text = getName();
        int textWidth = g.getFont().getWidth(text);
        int barWidth = Math.max(textWidth+6, 70);
        int barHeight = g.getFont().getLineHeight();
		Rectangle bar = new Rectangle(getCenterX() - barWidth/2, getMinY()-barHeight*3/2, barWidth, barHeight);
		if (camera.isOnScreen(bar) && !(this instanceof Player)) {
			
			float healthPercent = (float)getCurrentHealth()/getHealth();
	        int hpBarWidth = (int) (barWidth * healthPercent);
	        
	        g.setColor(BAR_BG);
	        g.fillRect(bar.getMinX()-camera.getMinX(), bar.getMinY()-camera.getMinY(), bar.getWidth(), bar.getHeight());
	        g.setColor(BAR);
	        g.fillRect(bar.getMinX()-camera.getMinX(), bar.getMinY()-camera.getMinY(), hpBarWidth, bar.getHeight());
	        g.setColor(VALUE);
	        g.drawString(getName(), getCenterX()-textWidth/2-camera.getMinX(), getMinY()-g.getFont().getLineHeight()*3/2-camera.getMinY());
		}
		super.render(g, camera);
	}
}
