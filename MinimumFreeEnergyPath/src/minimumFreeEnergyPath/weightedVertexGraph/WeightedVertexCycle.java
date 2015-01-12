package minimumFreeEnergyPath.weightedVertexGraph;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;

/** Class representing a cycle in the Molecular Shortest Path algorithm
 * @author Scott
 *
 */
public class WeightedVertexCycle {
	
	private ArrayList<WeightedVertex> vertices;
	
	/** Create a new cycle object
	 * @param graph The graph in which the cycle lies
	 * @param start Any one of the vertices in the cycle
	 */
	public WeightedVertexCycle(WeightedVertexGraph graph, WeightedVertex start) {
		vertices = new ArrayList<WeightedVertex>();
		this.add(start);
		
		// add all other connected vertices
		WeightedVertex next = start;
		do {
            DefaultWeightedEdge minEdge = graph.getMinEdge(next);
        	
        	next = graph.getEdgeTarget(minEdge);
        	
        	if (next.getCycle() != null) {
        		// already part of another cycle, merge them
        	}
        	this.add(next);
		} while (!this.contains(next));
	}
	
	/** Add a vertex to the cycle
	 * @param v The vertex to be added
	 */
	private void add(WeightedVertex v) {
		vertices.add(v);
		v.setCycle(this);
	}
	
	public List<WeightedVertex> getVertices() {
        return vertices;
	}
	
	public boolean contains(WeightedVertex v) {
		return vertices.contains(v);
	}
}
