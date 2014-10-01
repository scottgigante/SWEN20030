package game;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Bat extends PassiveMonster {
	private static final long serialVersionUID = 7096260413252608499L;
	/** Location of file description spawn positions */
	private static final String SPAWN_FILE = "assets/spawn/bat.txt";
	/** The location of the image */
	private static final String IMAGE_LOC = "assets/units/dreadbat.png";
	
	/* Stats */
	private static final String NAME = "Giant Bat";
	private static final int HEALTH = 40;
	
	/** Image file imported one and then stored statically */
	private static Image image;
	
	/** Load spawn locations from a file and create as many bats as appropriate
	 * @param world The world in which the bats live
	 * @return An arraylist of the created bats
	 */
	public static ArrayList<Bat> spawnAll(World world) {
		List<String> spawnList = FileReader.readFile(SPAWN_FILE);
		ArrayList<Bat> result = new ArrayList<Bat>();
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
			result.add(new Bat(pos, world));
		}
		return result;
	}
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for the bat's sprite
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
	public Bat(Vector2f pos, World world) {
		super(pos, getImage(), world, NAME, HEALTH);
		// TODO Auto-generated constructor stub
	}
}
