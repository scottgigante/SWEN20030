package game.object.item;

import game.framework.World;
import game.object.Item;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Sword extends Item {
	private static final long serialVersionUID = -2381008989866895301L;
	/** Beginning x position */
	private static final int SPAWN_X_POS = 4791;
	/** Beginning y position */
	private static final int SPAWN_Y_POS = 1253;
	/** The location of the image */
	private static final String IMAGE_LOC = "assets/items/sword.png";

	/** Item name to be displayed */
	private static final String NAME = "Sword of Strength";
	/** Health boost given, if any */
	private static final int HEALTH = 0;
	/** Damage boost given, if any */
	private static final int DAMAGE = 30;
	/** Cooldown boost given, if any */
	private static final int COOLDOWN = 0;

	/** Image file imported once and then stored statically */
	private static Image image;
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for the item's sprite
	 */
	private static Image getImage() {
		try {
			if (image == null) {
				image = new Image(IMAGE_LOC);
			}
			return image;
		} catch (SlickException e) {
			System.out.println(e.getMessage());
			System.exit(1);
			return null;
		}
	}

	/** Creates a new Sword object
	 * @param world The world to be created in
	 */
	public Sword(World world) {
		super(new Vector2f(SPAWN_X_POS,SPAWN_Y_POS), getImage(), world, NAME, HEALTH, DAMAGE, COOLDOWN);
	}

}
