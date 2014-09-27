/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Image;

public abstract class Character extends GameObject {
	private static final long serialVersionUID = -3778466348827433489L;
	/** The world in which the character exists */
	private World world;
	/** The image representing the character, not flipped */
	private Image sprite_nf;
	/** The image facing the opposite direction */
	private Image sprite_f;
	/** Max speed in pixels per millisecond */
	private float speed;
	/** Amount of health remaining */
	private int currentHealth;
	/** Time left before next possible attack */
	private int currentCooldown;
	/** The path currently being followed, if any */
	private Vector2f[] path;
	/** The position in the path array we have reached */
	private int path_index;
	/** Allows debugging removal of terrain blocking (or perhaps flying creatures) */
	private boolean terrain_blocking = true;
	
	/** Tolerance, relative to speed, which will be accepted in a radius around the destination of a path */
	private static final double PATH_TOLERANCE = 20;
	
	/** Constructor for character class
	 * @param x The initial central x-coordinate
	 * @param y	The initial central y-coordinate
	 * @param sprite An Image for the character
	 * @param map The map upon which it exists
	 */
	public Character(int x, int y, float speed, Image sprite, World world) {
		// Super constructor takes arguments to uppermost and rightmost coords, not centre
		super(x - sprite.getWidth()/2, y-sprite.getHeight()/2, sprite.getWidth(), sprite.getHeight());
		this.sprite_nf = sprite;
		sprite_f = sprite.getFlippedCopy(true, false);
		this.speed = speed;
		this.world = world;
	}
	
	/** Move the character in any direction specified either by keyboard or predefined path.
	 * Check first that movement is not blocked by terrain
	 * Updates whether or not to use flipped sprite
	 * @param x_dir Number of units to move along the x axis
	 * @param y_dir Number of units to move along the y axis
	 * @param delta Number of milliseconds since last movement
	 */
	public void update(float x_dir, float y_dir, int delta) {
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
			if (x_dir != 0 || y_dir != 0) {
				// Keyboard interrupts pathfinding
				path = null;
			} else {
				// Set phantom keyboard input to reach next waypoint
				// Use PATH_TOLERANCE/2 to avoid having to use sqrt(2)
				if (getCenterX() < path[path_index].x-PATH_TOLERANCE*speed/2) {
					x_dir = 1;
				} else if (getCenterX() > path[path_index].x+PATH_TOLERANCE*speed/2) {
					x_dir = -1;
				}
				if (getCenterY() < path[path_index].y-PATH_TOLERANCE*speed/2) {
					y_dir = 1;
				} else if (getCenterY() > path[path_index].y+PATH_TOLERANCE*speed/2) {
					y_dir = -1;
				}				
			}
		}
		
		// Find coords for the corner in the direction of movement, and flip sprite where necessary
		if (x_dir > 0) {
			x = getMaxX();
			if (getSprite() == sprite_f) {
			setSprite(sprite_nf);
			}
		} else if (x_dir < 0) {
			x = getMinX();
			if (getSprite() == sprite_nf) {
				setSprite(sprite_f);
			}
		}
		
		if (world.canMove(this,speed*x_dir*delta,0)) {
			// Movement okay in x direction
			setCenterX(getCenterX() + (float)(speed*x_dir*delta));		
		}
		if (world.canMove(this,0,speed*y_dir*delta)) {
			// Movement okay in y direction
			setCenterY(getCenterY() + (float)(speed*y_dir*delta));	
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
		Vector2f[] new_path = world.findPath(start, stop);
		if (new_path != null) {
			path = new_path;
			if (new_path.length > 1) {
				// we're already at the first node on the path, don't need to go there.
				path_index = 1;
			} else {
				path_index = 0;
			}
		}
	}	
}
