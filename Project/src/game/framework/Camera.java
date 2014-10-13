/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game.framework;

import game.object.Character;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/** Represents the camera that controls our viewpoint.
 */
public class Camera
{	
    /** The unit this camera is following */
    private GameObject unitFollow;
    /** The map that the camera will draw as a background */
    private Map map;
    
    /** Screen width, in pixels. */
    private final int screenWidth;
    /** Screen height, in pixels. */
    private final int screenHeight;

	/** The camera's position in the world, in x coordinates. */
    private int xPos;
	/** The camera's position in the world, in y coordinates. */
    private int yPos;

    /* Getters and setters */
    protected int getScreenWidth() {
		return screenWidth;
	}

	protected int getScreenHeight() {
		return screenHeight;
	}
	
    protected int getxPos() {
    	return xPos;
    }

    protected int getyPos() {
    	return yPos;
    }
    
    /** Returns the minimum x value on screen 
     */
    public int getMinX(){
    	return xPos-screenWidth/2;
    }
    
    /** Returns the maximum x value on screen 
     */
    public int getMaxX(){
    	return xPos+screenWidth/2;
    }
    
    /** Returns the minimum y value on screen 
     */
    public int getMinY(){
    	return yPos-screenHeight/2;
    }
    
    /** Returns the maximum y value on screen 
     */
    public int getMaxY(){
    	return yPos+screenHeight/2;
    }
    
    /** Create a new Camera object. 
     * @param player The initial character to be followed
     * @param map The map to be drawn
     * @param screenwidth Width of the screen, in pixels
     * @param screenheight Height of the screen, in pixels
     */
    public Camera(Character player, Map map, int screenwidth, int screenheight)
    throws SlickException
    {   
        this.screenWidth = screenwidth;
        this.screenHeight = screenheight;
        this.map = map;
        
        followUnit(player);
    }

    /** Update the game camera to recentre it's viewpoint around the player 
     */
    protected void update()
    throws SlickException
    {
        if (unitFollow != null) {
        	// centre on the followed object
        	xPos = (int)unitFollow.getCenterX();
        	yPos = (int)unitFollow.getCenterY();
        	// check we haven't gone off the screen
        	if (getMinX() < 0) {
        		xPos = screenWidth/2;
        	} else if (getMaxX() > map.getWidth()*map.getTileWidth()) {
        		xPos = map.getWidth()*map.getTileWidth() - screenWidth/2;
        	}
        	if (getMinY() < 0) {
        		yPos = screenHeight/2;
        	} else if (getMaxY() > map.getHeight()*map.getTileWidth()) {
        		yPos = map.getHeight()*map.getTileWidth() - screenHeight/2;
        	}
        }
    }

    /** Tells the camera to follow a given unit and updates to that position
     * @param unit The Character object to be followed
     */
    private void followUnit(GameObject unit)
    throws SlickException
    {
        unitFollow = unit;
        update();
    }
    
    /** Check whether a rectangle has any portion of it on screen
     * @param rect The rectangle to be checked
     */
    public boolean isOnScreen(Rectangle rect) {
    	return rect.getMinX() < getMaxX() && rect.getMaxX() > getMinX() && rect.getMinY() < getMaxY() && rect.getMaxY() > getMinY();
    }
}