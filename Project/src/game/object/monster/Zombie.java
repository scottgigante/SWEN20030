package game.object.monster;

import game.framework.FileReader;
import game.framework.World;
import game.object.AggressiveMonster;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Zombie extends AggressiveMonster {
	private static final long serialVersionUID = 1921378895919035582L;
	/** Location of file description spawn positions */
	private static final String SPAWN_FILE = "assets/spawn/zombie.txt";
	/** The location of the image */
	private static final String IMAGE_LOC = "assets/units/zombie.png";
	
	/* Stats */
	private static final String NAME = "Zombie";
	private static final int HEALTH = 60;
	private static final int DAMAGE = 10;
	private static final int COOLDOWN = 800;
	
	/** Image file imported one and then stored statically */
	private static Image image;
	
	/** Load spawn locations from a file and create as many zombies as appropriate
	 * @param world The world in which the zombies live
	 * @return An arraylist of the created zombies
	 */
	public static ArrayList<Zombie> spawnAll(World world) {
		ArrayList<Zombie> result = new ArrayList<Zombie>();
		for (Vector2f pos:FileReader.spawnAll(SPAWN_FILE)) {
			result.add(new Zombie(pos, world));
		}
		return result;
	}
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for the zombie's sprite
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
	public Zombie(Vector2f pos, World world) {
		super(pos, getImage(), world, NAME, HEALTH, DAMAGE, COOLDOWN);
	}
}