package game.object.monster;

import framework.GameObject;
import framework.World;
import game.object.AggressiveMonster;
import game.object.Character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Draelic extends AggressiveMonster {
	private static final long serialVersionUID = -7883804689005203936L;
	/** Initial x position, in pixels */
	private static final int SPAWN_X_POS = 2069;
	/** Initial y position, in pixels */
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
	/** The item to be guarded */
	private GameObject guard;
	
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
	 * @param world The world in which it lives
	 */
	public Draelic(World world, GameObject guard) {
		super(new Vector2f(SPAWN_X_POS, SPAWN_Y_POS), getImage(), world, NAME, MAX_SPEED, HEALTH, DAMAGE, COOLDOWN);
		this.guard = guard;		
	}
	
	/* (non-Javadoc)
	 * Has seen the player, now chase until it is dead.
	 * @see game.Character#attack(game.Character)
	 */
	@Override
	protected boolean attack(Character o) {
		chase = o;
		if (super.attack(o)) {
			setPath(guard.getVectorCenter());
			chase = null;
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * Either chase the player until it is dead, or return to the elixir.
	 * @see game.Monster#wander()
	 */
	@Override
	protected Vector2f wander() {
		if (chase != null) {
			setPath(chase);
		}
		if (!getPath().hasPath()) {
			setPath(guard);
		}
		return new Vector2f(0,0);
	}
}
