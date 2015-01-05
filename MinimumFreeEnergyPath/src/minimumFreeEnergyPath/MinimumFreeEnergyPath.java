package minimumFreeEnergyPath;

import java.io.FileNotFoundException;
import java.util.Set;

import org.jgrapht.alg.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


public class MinimumFreeEnergyPath {
	
	private static final String filename = "dat/data.dat";
	
	private static SimpleWeightedGraph<Vertex, DefaultWeightedEdge> g;

	public static void main(String[] args) {
		try {
			g = FileToGraphReader.readGraph(filename);
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " not found.");
			System.exit(0);
		}
		
		PrimMinimumSpanningTree<Vertex, DefaultWeightedEdge> prim = new PrimMinimumSpanningTree<Vertex, DefaultWeightedEdge>(g);
		
		Set<DefaultWeightedEdge> tree = prim.getMinimumSpanningTreeEdgeSet();		
		
	}
}
