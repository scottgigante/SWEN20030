package minimumFreeEnergyPath.weightedVertexGraph;

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

}
