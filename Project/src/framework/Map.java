/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package framework;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class Map extends TiledMap {
	/** The location of the file containing map data */
	private static final String MAP_LOC = "assets/map.tmx";
	/** The location of the resources relating to the map */
	private static final String RESOURCE_LOC = "assets";
	
	/** Constructor creates a new Map object */
	public Map() throws SlickException {
		super(MAP_LOC, RESOURCE_LOC);
	}
	
	/** Gets the width of the map, in pixels
	 * @return Integer width of map
	 */
	protected int getPixelWidth() {
		return getWidth()*getTileWidth();
	}
	
	/** Gets the height of the map, in pixels
	 * @return Integer height of map
	 */
	protected int getPixelHeight() {
		return getHeight()*getTileHeight();
	}
	
	/** Checks if the tile at x, y is walkable or not
	 * @param x Integer representing tile grid position
	 * @param y Integer representing tile grid position
	 * @return True, tile is walkable. False, tile is not walkable.
	 */
	protected boolean isWalkable(int x, int y) {
		return getTileProperty(getTileId(x, y, 0), "block", "0") == "0";
	}
	
	/** Checks if the tile at x, y is walkable or not
	 * @param x Float representing map position
	 * @param y Float representing map position
	 * @return True, tile is walkable. False, tile is not walkable.
	 */
	protected boolean isWalkable(float x, float y) {
		return isWalkable((int)(x/getTileWidth()), (int)(y/getTileHeight()));
	}
	
	/** Check all four corners of a rectangle moving a particular distance are walkable
	 * and if the rectangle will still be on the map
	 * @param rect The rectangle to be moved
	 * @param x Float to be moved in x direction
	 * @param y float to be moved in y direction
	 * @return True, rectangle can move legally. False, it cannot
	 */
	public boolean canMove(Rectangle rect, float x, float y) {
		try {
			if (rect.getMinX() + x < 0 || rect.getMaxX() + x > getPixelWidth() || rect.getMinY()+y < 0 || rect.getMaxY() + y > getPixelHeight()) {
				// walking off the map
				return false;
			}
	
	    	// Check for terrain blocking
			if (!isWalkable(rect.getMinX()+x, rect.getMinY()+y) || !isWalkable(rect.getMaxX()+x, rect.getMaxY()+y) || !isWalkable(rect.getMinX()+x,rect.getMaxY()+y) || !isWalkable(rect.getMaxX()+x, rect.getMinY()+y)) {
				// any one of the four corners is unwalkable
				return false;
			}
			
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	/** Renders a tiled map behind within the constraints of the camera's view.
	 * @param camera The viewport in which to draw
     */
	protected void render(Camera camera) {
    	// Calculate which tiles to start drawing from
    	int sx = camera.getMinX() / getTileWidth();
    	int sy = camera.getMinY() / getTileHeight();
    	// Calculate the overhang off the edge of the screen
    	int x = sx * getTileWidth() - camera.getMinX();
    	int y = sy * getTileHeight() - camera.getMinY();
    	super.render(x, y, sx, sy, camera.getScreenWidth(), camera.getScreenHeight());
    }
}


