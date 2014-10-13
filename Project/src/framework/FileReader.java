package framework;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public final class FileReader {
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	/** Reads all lines in a text file
	 * @param fileLoc A string referring to the location of the file
	 * @return A list of strings, one for each line
	 */
	public static List<String> readFile(String fileLoc) {
		try {
			Path path = Paths.get(fileLoc);
			return Files.readAllLines(path, ENCODING);
		} catch (IOException e) {
			System.out.print(e.getMessage());
			System.exit(1);
			return null;
		}
	}
	
	/** Load spawn locations from a file
	 * @return An arraylist of positions to create objects at
	 */
	public static ArrayList<Vector2f> spawnAll(String spawnFile) {
		List<String> spawnList = readFile(spawnFile);
		ArrayList<Vector2f> result = new ArrayList<Vector2f>();
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
			result.add(pos);
		}
		return result;
	}
}
