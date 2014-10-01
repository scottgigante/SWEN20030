/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{	
	/** Proximity, in pixels, to be considered 'near' */
	private static final int NEAR_DISTANCE = 30;
			
	/** The map containing tiles, images etc */
	private Map map;
	/** Human player character */
	private Player player;
	/** Status bar displays player stats */
	private StatusBar status;
	/** Camera to view the world */
	private Camera camera;
	/** List of all non-player GameObjects to be rendered, updated etc */
	private ArrayList<GameObject> objectList;
	/** List of all GameObjects to be destroyed this frame */
	private ArrayList<GameObject> destroyList;
	
    /** Create a new World object. */
    public World(int screenwidth, int screenheight)
    throws SlickException
    {
        map = new Map();
        player = new Player(this);
        status = new StatusBar(player);
        objectList = new ArrayList<GameObject>();
        destroyList = new ArrayList<GameObject>();
        objectList.add(new Aldric(this));
        objectList.add(new Elvira(this));
        objectList.add(new Garth(this));
        objectList.addAll(Bandit.spawnAll(this));
        objectList.addAll(Zombie.spawnAll(this));
        objectList.addAll(Skeleton.spawnAll(this));
        objectList.addAll(Bat.spawnAll(this));
        objectList.add(new Draelic(this));
        camera = new Camera(player, map, screenwidth, screenheight);
    }
    
    /** Update the game state for a frame when the mouse button is pressed
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     * @param mouseX The x position of the mouse.
     * @param mouseY The y position of the mouse.
     */
    public void update(float dirX, float dirY, int delta, boolean mousePressed, int mouseX, int mouseY, boolean aPressed, boolean tPressed)
    throws SlickException
    {
    	if (mousePressed) {
    		player.update(dirX, dirY, delta, camera.getMinX()+mouseX, camera.getMinY()+mouseY, aPressed, tPressed);
    	} else {
    		player.update(dirX, dirY, delta, aPressed, tPressed);
        }
    	for (GameObject o:objectList) {
    		if (o instanceof Monster) {
    			((Monster) o).update(player, delta);
    		}
    	}
    	camera.update();
    	for (GameObject o:objectList) {
    		if (o.dist(player) <= NEAR_DISTANCE) {
    			player.interact(o);
    			if (o instanceof AggressiveMonster) {
    				o.interact(player);
    			}
    		}
    	}
    	objectList.removeAll(destroyList);
    	destroyList.clear();
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
    	map.render(camera);
    	player.render(g, camera);
    	for (GameObject o:objectList) {
    		o.render(g, camera);
    	}
    	status.render(g);
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
			if (!map.canMove(rect,x,y) && (!(rect instanceof Character) || ((Character)rect).isTerrainBlocking())) {
				return false;
			}
			
			// TODO check collisions
		}

		return true;
    }
    
    /** Checks whether one GameObject has a direct line of sight to another
     * @param o1 The first GameObject
     * @param o2 The second GameObject - if terrainBlocking is false, this will nearly always return true
     * @return True or false
     */
    public boolean hasLineOfSight(GameObject o1, GameObject o2) {
    	if (o1.dist(o2) < NEAR_DISTANCE) {
    		// no point, they're already on top of each other
    		return true;
    	}
    	boolean success = true;
    	float distX = o1.getCenterX()-o2.getCenterX(), distY = o1.getCenterY()-o2.getCenterY();
		float x=o2.getCenterX(),y=o2.getCenterY();
		while (Math.abs(o1.getCenterX()-x) > map.getTileWidth()) {
			if (distX > distY) {
				// x is the bounding coordinate, scale the y
				x += map.getTileWidth()*Math.signum(distX);
				y += map.getTileHeight()*Math.signum(distY)*distY/distX;
			} else {
				// y is the bounding coordinate
				x += map.getTileWidth()*Math.signum(distX)*distX/distY;
				y += map.getTileHeight()*Math.signum(distY);						
			}
			if (!map.canMove(o2, x, y)) {
				success = false;
				break;
			}
		}
		return success;
    }
    
    /** Remove an object from the object list
     * @param o GameObject to be removed
     */
    public void removeObject(GameObject o) {
    	destroyList.add(o);
    }
}
