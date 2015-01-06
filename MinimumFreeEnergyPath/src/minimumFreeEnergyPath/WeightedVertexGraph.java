package minimumFreeEnergyPath;

import org.jgrapht.graph.SimpleWeightedGraph;

public class WeightedVertexGraph<E> extends SimpleWeightedGraph<WeightedVertex, E> {
	private static final long serialVersionUID = 1L;

	public WeightedVertexGraph(Class<? extends E> edgeClass) {
		super(edgeClass);
	}
	
	public E addEdge(WeightedVertex sourceVertex, WeightedVertex targetVertex) {
		E e = super.addEdge(sourceVertex, targetVertex);
		this.setEdgeWeight(e, Math.abs(targetVertex.getWeight()-sourceVertex.getWeight()));
		return e;
	}

}
