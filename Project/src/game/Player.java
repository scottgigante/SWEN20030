/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayList;

/** Player-character class.
 * Adds individual player functions to the generic character class
 */
public class Player extends Character {	
	private static final long serialVersionUID = 8734732098037662995L;
	/** Beginning x position */
	private static final int SPAWN_X_POS = 756;
	/** Beginning y position */
	private static final int SPAWN_Y_POS = 684;
	/** The location of the player image */
	private static final String IMAGE_LOC = "assets/player.png";
	
	/* Player's stats */
	private static final String NAME = "Player";
	private static final float MAX_SPEED = 0.25f;
	private static final int HEALTH = 100;
	private static final int DAMAGE = 26;
	private static final int COOLDOWN = 600;
	
	/** Image file imported one and then stored statically */
	private static Image image;
	
	/** Player's inventory, consisting of up to four items */
	private ArrayList<Item> itemList;
	/** Whether or not the player will speak to an NPC it interacts with */
	private boolean isSpeak;
	/** Whether or not the player will attack a monster it interacts with */
	private boolean isAttack;
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for the player's sprite
	 * @throws SlickException
	 */
	private static Image getImage() throws SlickException {
		if (image == null) {
			image = new Image(IMAGE_LOC);
		}
		return image;
	}
	
	/** Constructor for the player class
	 * @param world The world in which the player lives
	 * @throws SlickException 
	 */
	public Player(World world) throws SlickException {
		super(SPAWN_X_POS, SPAWN_Y_POS, getImage(), world, NAME, MAX_SPEED, HEALTH, DAMAGE, COOLDOWN);
		setHealth(HEALTH);
		setDamage(DAMAGE);
		setCooldown(COOLDOWN);
	}
	
	public void interact(GameObject o) {
		// TODO stub
	}
	
	/** Updates the player with a mouse press
	 * @param xDir x direction on keyboard
	 * @param yDir y direction on keyboard
	 * @param delta Milliseconds since last update
	 * @param mouseX x position of pointer when pressed relative to map
	 * @param mouseY y position of pointer when pressed relative to map
	 * @param aPressed Whether or not A has been pressed
	 * @param tPressed Whether or not T has been pressed
	 */
	public void update(float dirX, float dirY, int delta, int mouseX, int mouseY, boolean aPressed, boolean tPressed) {
		setPath(mouseX, mouseY);
		update(dirX,dirY,delta, aPressed,tPressed);
	}
	
	/** Updates the player for key presses
	 * @param xDir x direction on keyboard
	 * @param yDir y direction on keyboard
	 * @param delta Milliseconds since last update
	 * @param aPressed Whether or not A has been pressed
	 * @param tPressed Whether or not T has been pressed
	 */
	public void update(float dirX, float dirY, int delta, boolean aPressed, boolean tPressed) {
		isSpeak = aPressed;
		isAttack = tPressed;
		update(dirX,dirY,delta);
	}
	
	public void destroy() {
		this.x = SPAWN_X_POS;
		this.y = SPAWN_Y_POS;
		setCurrentHealth(getHealth());
		setCurrentCooldown(0);
	}
}
