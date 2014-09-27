package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Elvira extends NPC {

	/** Beginning x position */
	private static final float SPAWN_X_POS = 738;
	/** Beginning y position */
	private static final float SPAWN_Y_POS = 549;
	/** The location of the player image */
	private static final String IMAGE_LOC = "assets/units/shaman.png";
	/** Name to be displayed in health bar */
	private static final String NAME = "Elvira";
	/** Location of dialogue file */
	private static final String DIALOGUE_LOC = "assets/dialogue/elvira.txt";

	/** Image file imported one and then stored statically */
	private static Image image;
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for Elvira's sprite
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
	
	/** Constructor creates Elvira */
	public Elvira() {
		super(new Vector2f(SPAWN_X_POS, SPAWN_Y_POS), getImage(), NAME, DIALOGUE_LOC);
	}

	@Override
	public void interact(GameObject o) {
		// TODO Auto-generated method stub

	}

}
