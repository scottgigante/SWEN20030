package game.object.item;

import game.framework.World;
import game.object.Item;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Amulet extends Item {
	private static final long serialVersionUID = -1752682954197805482L;
	/** Beginning x position */
	private static final int SPAWN_X_POS = 965;
	/** Beginning y position */
	private static final int SPAWN_Y_POS = 3563;
	/** The location of the image */
	private static final String IMAGE_LOC = "assets/items/amulet.png";

	/** Item name to be displayed */
	private static final String NAME = "Amulet of Vitality";
	/** Health boost given, if any */
	private static final int HEALTH = 80;
	/** Damage boost given, if any */
	private static final int DAMAGE = 0;
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
	
	public Amulet(World world) {
		super(new Vector2f(SPAWN_X_POS,SPAWN_Y_POS), getImage(), world, NAME, HEALTH, DAMAGE, COOLDOWN);
	}

}
