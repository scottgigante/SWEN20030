package game.object.npc;

import game.framework.GameObject;
import game.framework.World;
import game.object.NPC;
import game.object.Player;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Aldric extends NPC {
	private static final long serialVersionUID = 2029250095185699382L;
	/** Beginning x position */
	private static final float SPAWN_X_POS = 467;
	/** Beginning y position */
	private static final float SPAWN_Y_POS = 679;
	/** The location of the player image */
	private static final String IMAGE_LOC = "assets/units/prince.png";
	/** Name to be displayed in health bar */
	private static final String NAME = "Prince Aldric";
	/** Location of dialogue file */
	private static final String DIALOGUE_LOC = "assets/dialogue/aldric.txt";

	/** Image file imported one and then stored statically */
	private static Image image;
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for Aldric's sprite
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
	
	/** Constructor creates Aldric */
	public Aldric(World world) {
		super(new Vector2f(SPAWN_X_POS, SPAWN_Y_POS), getImage(), world, NAME, DIALOGUE_LOC);
	}

	@Override
	public void interact(GameObject o) {
		if (o instanceof Player) {
			if (((Player) o).hasElixir()) {
				speak(2);
			} else {
				speak(1);
			}
		}
	}

}
