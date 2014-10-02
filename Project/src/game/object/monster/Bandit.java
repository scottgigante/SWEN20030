package game.object.monster;

import game.framework.FileReader;
import game.framework.World;
import game.object.AggressiveMonster;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Bandit extends AggressiveMonster {
	private static final long serialVersionUID = -3640967800947380589L;
	/** Location of file description spawn positions */
	private static final String SPAWN_FILE = "assets/spawn/bandit.txt";
	/** The location of the image */
	private static final String IMAGE_LOC = "assets/units/bandit.png";
	
	/* Stats */
	private static final String NAME = "Bandit";
	private static final int HEALTH = 40;
	private static final int DAMAGE = 8;
	private static final int COOLDOWN = 200;
	
	/** Image file imported one and then stored statically */
	private static Image image;
	
	/** Load spawn locations from a file and create as many bandits as appropriate
	 * @param world The world in which the bandits live
	 * @return An arraylist of the created bandits
	 */
	public static ArrayList<Bandit> spawnAll(World world) {
		List<String> spawnList = FileReader.readFile(SPAWN_FILE);
		ArrayList<Bandit> result = new ArrayList<Bandit>();
		for (String s:spawnList) {
			String[] coords = s.split(",");
			Vector2f pos = new Vector2f();
			try {
				pos.x = Integer.parseInt(coords[0]);
				pos.y = Integer.parseInt(coords[1]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.print(e.getMessage());
				continue;
			} catch (NumberFormatException e) {
				System.out.print(e.getMessage());
				continue;			
			}
			result.add(new Bandit(pos, world));
		}
		return result;
	}
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for the bandit's sprite
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
	public Bandit(Vector2f pos, World world) {
		super(pos, getImage(), world, NAME, HEALTH, DAMAGE, COOLDOWN);
		// TODO Auto-generated constructor stub
	}
}
