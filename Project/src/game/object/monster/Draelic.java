package game.object.monster;

import game.framework.World;
import game.object.AggressiveMonster;
import game.object.Character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Draelic extends AggressiveMonster {
	private static final long serialVersionUID = -7883804689005203936L;
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
	
	/** Marks whether or not Draelic is chasing the player */
	private Character chase = null;
	
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
	}
	
	/* (non-Javadoc)
	 * Has seen the player, now chase until it is dead.
	 * @see game.Character#attack(game.Character)
	 */
	@Override
	public boolean attack(Character o) {
		chase = o;
		if (super.attack(o)) {
			setPath(SPAWN_X_POS, SPAWN_Y_POS);
			chase = null;
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * Either chase the player until it is dead, or stay put.
	 * @see game.Monster#wander()
	 */
	@Override
	public Vector2f wander() {
		if (chase != null) {
			setPath(chase.getCenterX(), chase.getCenterY());
		}
		return new Vector2f(0,0);
	}
}
