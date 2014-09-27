/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Player-character class.
 * Adds individual player functions to the generic character class
 */
public class Player extends Character {	
	private static final long serialVersionUID = 8734732098037662995L;
	/** Beginning x position */
	private static final int SPAWN_X_POS = 756;
	/** Beginning y position */
	private static final int SPAWN_Y_POS = 684;
	/** Default max speed */
	private static final float MAX_SPEED = 0.25f;
	/** The location of the player image */
	private static final String IMAGE_LOC = "assets/player.png";
	
	private static Image image;
	public static Image getImage() {
		if (image == null) {
			try {
			image = new Image(IMAGE_LOC);
			} catch (SlickException e) {
				System.out.print(e.getMessage());
				System.exit(1);
			}
		}
		return image;
	}
	
	/** Constructor for the player class
	 * @param sprite The Image to be used as the player's sprite
	 * @param map The map upon which the player exists
	 */
	public Player(Image image, World world) {
		super(SPAWN_X_POS, SPAWN_Y_POS, MAX_SPEED, image, world);
	}
	
	public void interact(GameObject o) {
		
	}
}
