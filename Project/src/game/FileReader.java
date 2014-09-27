package game;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
}
