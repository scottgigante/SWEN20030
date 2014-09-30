package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Draelic extends AggressiveMonster {
	/** Location of file description spawn positions */
	private static final int SPAWN_X_POS = 2069;
	private static final int SPAWN_Y_POS = 510;
	/** The location of the image */
	private static final String IMAGE_LOC = "assets/units/necromancer.png";
	
	/* Stats */
	private static final String NAME = "Draelic";
	private static final int HEALTH = 140;
	private static final int DAMAGE = 30;
	private static final int COOLDOWN = 400;
	private static final float MAX_SPEED = 0.3f;
	
	/** Image file imported one and then stored statically */
	private static Image image;
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for the draelic's sprite
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
	
	/** Constructor creates an object of the class
	 * @param pos Position at which it is created
	 * @param world The world in which it lives
	 */
	public Draelic(World world) {
		super(new Vector2f(SPAWN_X_POS, SPAWN_Y_POS), getImage(), world, NAME, MAX_SPEED, HEALTH, DAMAGE, COOLDOWN);
		// TODO Auto-generated constructor stub
	}
}
