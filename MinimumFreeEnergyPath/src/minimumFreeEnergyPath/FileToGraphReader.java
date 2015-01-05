package minimumFreeEnergyPath;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public final class FileToGraphReader {
	
	private FileToGraphReader() {
	}
	
	/** Parses double surrounded by non-numeric text */
	private static Double parseDouble(String s) {
		return Double.parseDouble(s.replaceAll("[^\\d.]", ""));
	}
	
	/* Reads TSV input file, outputs a graph */
	protected static SimpleWeightedGraph<Vertex,DefaultWeightedEdge> readGraph(String filename) throws FileNotFoundException {
		
		// read in input file
		Scanner scanner = new Scanner(new FileReader(filename));
		
		// create header array
		List<String> xStringHeader = new ArrayList<String>(Arrays.asList(scanner.nextLine().split("\\t")));
		xStringHeader.remove(0);
		ArrayList<Double> xHeader = new ArrayList<Double>();
		ArrayList<Double> yHeader = new ArrayList<Double>();
		for (String s : xStringHeader) {
			xHeader.add(parseDouble(s));
		}
		
		// read all values from table into vertex array
		VertexArray vertexArray = new VertexArray();
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split("\\t");
			double y = parseDouble(line[0]);
			yHeader.add(y);
			
			for (int i=1;i<line.length;i++) {
				try {
					vertexArray.add(new Vertex(xHeader.get(i-1),y,parseDouble(line[i])));
				} catch (NumberFormatException e) {
					// Inf found, ignore it
				}
			}
		}
		
		vertexArray.setXHeader(xHeader);
		vertexArray.setYHeader(yHeader);
		
		// add the root node to the graph - this helps with Prim's alg later
		SimpleWeightedGraph<Vertex, DefaultWeightedEdge> g = new SimpleWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		for (Vertex v : vertexArray) {
			if (vertexArray.isRoot(v)) {
				g.addVertex(v);
			}
		}
		// now add the rest of them
		for (Vertex v : vertexArray) {
			g.addVertex(v);
		}
		
		// add edges to graph
		for (Vertex v1 : vertexArray) {
			for (Vertex v2 : vertexArray) {
				// avoid doing this twice for every edge
				if (vertexArray.indexOf(v2) > vertexArray.indexOf(v1)) {
					if (vertexArray.isAdjacent(v1, v2)) {
						DefaultWeightedEdge e = g.addEdge(v1, v2);
						g.setEdgeWeight(e, Math.abs(v1.getWeight()-v2.getWeight()));
					}
				}
			}
		}
		
		// clean up
		scanner.close();
		return g;
	}
}
