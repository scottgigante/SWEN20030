/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Image;

public class Character extends Rectangle {
	private static final long serialVersionUID = -3778466348827433489L;
	/** The map upon which the character exists */
	private Map map;
	/** The image representing the character */
	private Image sprite;
	/** The image facing the opposite direction */
	private Image sprite_f;
	private Boolean sprite_flipped = false;
	/** Max speed in pixels per millisecond */
	private float speed;
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
	public Character(int x, int y, float speed, Image sprite, Map map) {
		// Super constructor takes arguments to uppermost and rightmost coords, not centre
		super(x - sprite.getWidth()/2, y-sprite.getHeight()/2, sprite.getWidth(), sprite.getHeight());
		this.sprite = sprite;
		sprite_f = sprite.getFlippedCopy(true, false);
		this.speed = speed;
		this.map = map;
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
		
		float x=0, y=0;
		// Find coords for the corner in the direction of movement, and flip sprite where necessary
		if (x_dir > 0) {
			x = getMaxX();
			if (sprite_flipped == true) {
			sprite_flipped = false;
			}
		} else if (x_dir < 0) {
			x = getMinX();
			if (sprite_flipped == false) {
				sprite_flipped = true;
			}
		}
		if (y_dir > 0) {
			y = getMaxY();
		} else if (y_dir < 0) {
			y = getMinY();
		}
		// Check for terrain blocking
		if (x_dir != 0 || y_dir != 0) {
			float x_new = x+speed*x_dir*delta;
			if (x_new < map.getWidth()*map.getTileWidth()) {
				if ((map.isWalkable(x_new, getMinY()) && map.isWalkable(x_new, getMaxY())) || !terrain_blocking) {
					// Movement okay in x direction
					setCenterX(getCenterX() + (float)(speed*x_dir*delta));		
				}
			}
			float y_new = y+speed*y_dir*delta;
			if (y_new < map.getHeight()*map.getTileHeight()) {
				if ((map.isWalkable(getMinX(), y_new) && map.isWalkable(getMaxX(), y_new)) || !terrain_blocking) {
					// Movement okay in y direction
					setCenterY(getCenterY() + (float)(speed*y_dir*delta));				
				}
			}
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
		Vector2f[] new_path = map.findPath(start, stop);
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
	
	
	/** Render a character within the scope of the camera
     * @param camera The viewport in which to draw
     */
    public void render(Camera camera) {
    	if (camera.isOnScreen(this)) {
    		// character is on-screen, draw it
    		// Check whether to draw original or flipped sprite
    		if (sprite_flipped) {
    			sprite_f.draw(getMinX()-camera.getMinX(),getMinY()-camera.getMinY());
    		} else {
    			sprite.draw(getMinX()-camera.getMinX(),getMinY()-camera.getMinY());
    		}
    	}
    }
}
