/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	/** The location of the player image */
	private static final String player_image = "assets/player.png";
	
	/** The map containing tiles, images etc */
	private Map map;
	/** Human player character */
	private Player player;
	/** Camera to view the world */
	private Camera camera;
	/** List of all non-player GameObjects to be rendered, updated etc */
	private ArrayList<GameObject> objectList;
	
    /** Create a new World object. */
    public World(int screenwidth, int screenheight)
    throws SlickException
    {
        map = new Map();
        Image sprite = new Image(player_image);
        player = new Player(sprite, map);
        camera = new Camera(player, map, screenwidth, screenheight);
    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(float dir_x, float dir_y, int delta)
    throws SlickException
    {
        player.update(dir_x, dir_y, delta);
        camera.update();
    }
    
    /** Update the game state for a frame when the mouse button is pressed
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     * @param mouse_x The x position of the mouse.
     * @param mouse_y The y position of the mouse.
     */
    public void update(float dir_x, float dir_y, int delta, int mouse_x, int mouse_y)
    throws SlickException
    {
    	player.setPath(camera.getMinX()+mouse_x, camera.getMinY()+mouse_y);
        update(dir_x, dir_y, delta);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
    	map.render(camera);
    	player.render(camera);
    }
    
    /** Passes on to map's find path algorithm
     * @param start The starting position of the path
     * @param stop The ending position of the path
     * @return Vector2f[] describing path
     */
    public Vector2f[] findPath(Vector2f start, Vector2f stop) {
    	return map.findPath(start, stop);
    }
    
    /** Checks if a rectangle can legally move in a direction
     * @param rect The rectangle to be moved
     * @param x Float to be moved in the x direction
     * @param y Float to be moved in the y direction
     * @return Boolean whether or not the rectangle can legally move
     */
    public boolean canMove(Rectangle rect, float x, float y) {
		if (x != 0 || y != 0) {
			// check map legality
			if (!map.canMove(rect,x,y)) {
				return false;
			}
			
			// TODO check collisions
		}

		return true;
    }
}
