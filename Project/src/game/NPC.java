package game;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.newdawn.slick.Image;

public abstract class NPC extends GameObject {

	private static int HEALTH = 1;
	private static int DAMAGE = 0;
	private static int COOLDOWN = 0;
	
	private List<String> dialogue;
	final static Charset ENCODING = StandardCharsets.UTF_8;
	  
	public NPC(float x, float y, Image sprite, String name, String dialogueLoc) {
		super(x, y, sprite, name, HEALTH, DAMAGE, COOLDOWN);
		dialogue = readFile(dialogueLoc);		
	}
	
	/** Reads all lines in a text file
	 * @param fileLoc A string referring to the location of the file
	 * @return A list of strings, one for each line
	 */
	private List<String> readFile(String fileLoc) {
		try {
			Path path = Paths.get(fileLoc);
			return Files.readAllLines(path, ENCODING);
		} catch (IOException e) {
			System.out.print(e.getMessage());
			System.exit(1);
			return null;
		}
	}
	
	public void speak(int line) {
		// TODO stub
	}

}
