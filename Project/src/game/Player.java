/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game;

import org.newdawn.slick.Image;

/** Player-character class.
 * Adds individual player functions to the generic character class
 */
public class Player extends Character {	
	private static final long serialVersionUID = 8734732098037662995L;
	/** Beginning x position */
	private static final int initial_xPos = 756;
	/** Beginning y position */
	private static final int initial_yPos = 684;
	/** Default max speed */
	private static final float max_speed = 0.25f;
	
	/** Constructor for the player class
	 * @param sprite The Image to be used as the player's sprite
	 * @param map The map upon which the player exists
	 */
	public Player(Image sprite, Map map) {
		super(initial_xPos, initial_yPos, max_speed, sprite, map);
	}
}
