package minimumFreeEnergyPath.weightedVertexGraph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class WeightedVertexGraph extends SimpleDirectedWeightedGraph<WeightedVertex, DefaultWeightedEdge> {
	private static final long serialVersionUID = 1L;
	
	private WeightedVertex startVertex;
	private WeightedVertex endVertex;

	public WeightedVertexGraph() {
		super(DefaultWeightedEdge.class);
	}
	
	public DefaultWeightedEdge addEdge(WeightedVertex sourceVertex, WeightedVertex targetVertex) {
		DefaultWeightedEdge e = super.addEdge(sourceVertex, targetVertex);
		this.setEdgeWeight(e, targetVertex.getWeight()-sourceVertex.getWeight());
		return e;
	}
	
	public void setStartVertex(WeightedVertex v) {
		this.startVertex = v;
	}
	
	public void setEndVertex(WeightedVertex v) {
		this.endVertex = v;
	}

	public WeightedVertex getStartVertex() {
		return startVertex;
	}

	public WeightedVertex getEndVertex() {
		return endVertex;
	}

	/** Gets the outgoing edges from a vertex and its attributed cycle if one exists
	 * @param vertex The vertex to begin from
	 * @return A set of edges
	 */
	private Set<DefaultWeightedEdge> outgoingEdgesOfCycle(WeightedVertex vertex) {
		WeightedVertexCycle cycle = vertex.getCycle();
		if (cycle != null) {
			Set<DefaultWeightedEdge> edgeSet = new HashSet<DefaultWeightedEdge>();
			// add all outgoing edges
			for (WeightedVertex v : cycle.getVertices()) {
				edgeSet.addAll(super.outgoingEdgesOf(v));
			}
			
			// remove internal edges
			for (Iterator<DefaultWeightedEdge> i = edgeSet.iterator(); i.hasNext();) {
			    DefaultWeightedEdge e = i.next();
			    if (cycle.contains(this.getEdgeTarget(e))) {
					i.remove();
				}
			}
			return edgeSet;
		} else {
			return super.outgoingEdgesOf(vertex);
		}
	}
	
	private DefaultWeightedEdge minEdgeOfSet(Set<DefaultWeightedEdge> edgeSet) {
		double minWeight = Double.POSITIVE_INFINITY;
    	DefaultWeightedEdge minEdge = null;
    	
    	// take the smallest edge attached to current node
    	for (DefaultWeightedEdge e : edgeSet) {
    		double w = this.getEdgeWeight(e);
    		if (w < minWeight) {
    			minEdge = e;
    			minWeight = w;
    		}
    	}
    	
    	return minEdge;
	}
	
	/** Finds the lowest (or most negative) weighted edge outgoing from a vertex
	 * @param vertex The vertex to be searched
	 * @return The lowest weighted edge
	 */
	public DefaultWeightedEdge getMinEdge(WeightedVertex vertex) {
		return minEdgeOfSet(this.outgoingEdgesOf(vertex));
	}
	
	/** Finds the lowest (or most negative) weighted edge outgoing from a cycle
	 * @param vertex Any one of the vertices contained in the cycle
	 * @return The lowest weighted edge
	 */
	public DefaultWeightedEdge getMinEdgeInCycle(WeightedVertex vertex) {
    	return minEdgeOfSet(this.outgoingEdgesOfCycle(vertex));
	}
}
