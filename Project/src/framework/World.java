/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package framework;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import game.object.AggressiveMonster;
import game.object.Character;
import game.object.Monster;
import game.object.NPC;
import game.object.Player;
import game.object.item.Amulet;
import game.object.item.Elixir;
import game.object.item.Sword;
import game.object.item.Tome;
import game.object.monster.Bandit;
import game.object.monster.Bat;
import game.object.monster.Draelic;
import game.object.monster.Skeleton;
import game.object.monster.Zombie;
import game.object.npc.Aldric;
import game.object.npc.Elvira;
import game.object.npc.Garth;

import java.util.ArrayList;
import java.util.List;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{	
	/** Proximity, in pixels, to be considered 'near' */
	private static final int NEAR_DISTANCE = 10;
	/** Factor by which to scale tile width for line of sight */
	private static final float SCALE_FACTOR = 0.25f;
	/** Time to display the splash screen */
	private static final int SPLASH_TIME = 5000;
	/** Location of the splash text file */
	private static final String SPLASH_LOC = "assets/dialogue/splash.txt";
			
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
	/** List of all GameObjects to be created this frame */
	private ArrayList<GameObject> createList;
	/** Time remaining to display the splash screen */
	private int currentSplashTime;
	/** Text to be displayed as splash */
	private List<String> splashText;
	
	protected Map getMap() {
		return this.map;
	}
	
    /** Create a new World object. */
    public World(int screenwidth, int screenheight)
    throws SlickException
    {
        map = new Map();
        player = new Player(this);
        camera = new Camera(player, map, screenwidth, screenheight);
        status = new StatusBar(player);
        objectList = new ArrayList<GameObject>();
        destroyList = new ArrayList<GameObject>();
        createList = new ArrayList<GameObject>();
        GameObject elixir = new Elixir(this);
        objectList.add(elixir);
        objectList.add(new Amulet(this));
        objectList.add(new Sword(this));
        objectList.add(new Tome(this));
        objectList.add(new Aldric(this));
        objectList.add(new Elvira(this));
        objectList.add(new Garth(this));
        objectList.addAll(Bandit.spawnAll(this));
        objectList.addAll(Zombie.spawnAll(this));
        objectList.addAll(Skeleton.spawnAll(this));
        objectList.addAll(Bat.spawnAll(this));
        // Draelic protects the elixir
        objectList.add(new Draelic(this, elixir));
        currentSplashTime = SPLASH_TIME;
        splashText = FileReader.readFile(SPLASH_LOC);
        
    }
    
    /** Update the game state for a frame when the mouse button is pressed
     * @param dir The player's movement in the x, y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     * @param mouseX The x position of the mouse.
     * @param mouseY The y position of the mouse.
     */
    protected void update(Vector2f dir, int delta, boolean mousePressed, int mouseX, int mouseY, boolean aPressed, boolean tPressed)
    throws SlickException
    {
    	// Update the player with I/O
    	if (mousePressed) {
    		player.update(dir, delta, new Vector2f(camera.getMinX()+mouseX, camera.getMinY()+mouseY), aPressed, tPressed);
    	} else {
    		player.update(dir, delta, aPressed, tPressed);
        }
    	// Update all objects
    	for (GameObject o:objectList) {
    		if (o instanceof Monster) {
    			((Monster) o).update(player, delta);
    		} else if (o instanceof NPC) {
    			((NPC) o).update(delta);
    		}
    	}
    	camera.update();
    	// Deal with interactions between objects
    	for (GameObject o:objectList) {
    		if (o.dist(player) <= NEAR_DISTANCE) {
    			player.interact(o);
    			if (o instanceof AggressiveMonster) {
    				// AggressiveMonster interacts of its own accord
    				o.interact(player);
    			}
    		}
    	}
    	// Update the object list
    	objectList.removeAll(destroyList);
    	objectList.addAll(createList);
    	destroyList.clear();
    	createList.clear();
    	if (currentSplashTime > 0) {
    		currentSplashTime -= delta;
    	}
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    protected void render(Graphics g)
    throws SlickException
    {
    	map.render(camera);
    	player.render(g, camera);
    	for (GameObject o:objectList) {
    		o.render(g, camera);
    	}
    	status.render(g);
    	if (currentSplashTime > 0) {
    		TextRenderer.renderLines(g, RPG.SCREEN_WIDTH/2, RPG.SCREEN_HEIGHT/2, splashText);
    	}
    }
    
    /** Checks if a rectangle can legally move in a direction
     * @param rect The rectangle to be moved
     * @param x Float to be moved in the x direction
     * @param y Float to be moved in the y direction
     * @return Boolean whether or not the rectangle can legally move
     */
    public boolean canMove(Character c, float x, float y) {
		if ((x != 0 || y != 0) && c.isTerrainBlocking()) {
			// check map legality
			if (!map.canMove(c,x,y)) {
				return false;
			}
			
			// check collisions by moving, checking intersection, then moving back
			c.setX(c.getX()+x);
			c.setY(c.getY()+y);
			for (GameObject o:objectList) {
				if (o.intersects(c) && o != c) {
					c.setX(c.getX()-x);
					c.setY(c.getY()-y);
					return false;
				}
			}
			// check player separately, it's not in objectList
			if (player.intersects(c) && player != c) {
				c.setX(c.getX()-x);
				c.setY(c.getY()-y);
				return false;
			}
			c.setX(c.getX()-x);
			c.setY(c.getY()-y);
			
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
		float x=0,y=0;
		float scaleX, scaleY;
		if (Math.abs(distX) > Math.abs(distY)) {
			// x is the bounding coordinate, scale the y
			scaleX = SCALE_FACTOR;
			scaleY = Math.abs(distY/distX)*SCALE_FACTOR;
		} else {
			// y is the bounding coordinate
			scaleX = Math.abs(distX/distY)*SCALE_FACTOR;
			scaleY = SCALE_FACTOR;
		}
		while (Math.abs(distX-x) > map.getTileWidth() || Math.abs(distY-y) > map.getTileHeight() && Math.abs(x) < Math.abs(distX)) {
			x += map.getTileWidth()*Math.signum(distX)*scaleX;
			y += map.getTileHeight()*Math.signum(distY)*scaleY;
			if (!map.canMove(o2, x, y)) {
				success = false;
				break;
			}
		}
		return success;
    }
    
    /** Add an object to the object list
     * @param o GameObject to be added
     */
    public void createObject(GameObject o) {
    	createList.add(o);
    }
    
    /** Remove an object from the object list
     * @param o GameObject to be removed
     */
    public void removeObject(GameObject o) {
    	destroyList.add(o);
    }
}
