package game.object.monster;

import framework.FileReader;
import framework.World;
import game.object.AggressiveMonster;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Skeleton extends AggressiveMonster {
	private static final long serialVersionUID = 7938227035760180122L;
	/** Location of file description spawn positions */
	private static final String SPAWN_FILE = "assets/spawn/skeleton.txt";
	/** The location of the image */
	private static final String IMAGE_LOC = "assets/units/skeleton.png";
	
	/* Stats */
	private static final String NAME = "Skeleton";
	private static final int HEALTH = 100;
	private static final int DAMAGE = 16;
	private static final int COOLDOWN = 500;
	
	/** Image file imported one and then stored statically */
	private static Image image;
	
	/** Load spawn locations from a file and create as many skeletons as appropriate
	 * @param world The world in which the skeletons live
	 * @return An arraylist of the created skeletons
	 */
	public static ArrayList<Skeleton> spawnAll(World world) {
		ArrayList<Skeleton> result = new ArrayList<Skeleton>();
		for (Vector2f pos:FileReader.spawnAll(SPAWN_FILE)) {
			result.add(new Skeleton(pos, world));
		}
		return result;
	}
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for the skeleton's sprite
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
	public Skeleton(Vector2f pos, World world) {
		super(pos, getImage(), world, NAME, HEALTH, DAMAGE, COOLDOWN);
	}
}
