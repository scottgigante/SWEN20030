package game.object.npc;

import game.framework.GameObject;
import game.framework.World;
import game.object.NPC;
import game.object.Player;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Garth extends NPC {
	private static final long serialVersionUID = -2399766907581161525L;
	/** Beginning x position */
	private static final float SPAWN_X_POS = 756;
	/** Beginning y position */
	private static final float SPAWN_Y_POS = 870;
	/** The location of the player image */
	private static final String IMAGE_LOC = "assets/units/peasant.png";
	/** Name to be displayed in health bar */
	private static final String NAME = "Garth";
	/** Location of dialogue file */
	private static final String DIALOGUE_LOC = "assets/dialogue/garth.txt";

	/** Image file imported one and then stored statically */
	private static Image image;
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for Garth's sprite
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
	
	/** Constructor creates Garth */
	public Garth(World world) {
		super(new Vector2f(SPAWN_X_POS, SPAWN_Y_POS), getImage(), world, NAME, DIALOGUE_LOC);
	}

	@Override
	public void interact(GameObject o) {
		if (o instanceof Player) {
			if (!((Player) o).hasAmulet()) {
				speak(1);
			} else if (!((Player) o).hasTome()) {
				speak(2);
			} else if (!((Player) o).hasSword()) {
				speak(3);
			} else {
				speak(4);
			}
		}
	}

}
