package minimumFreeEnergyPath.weightedVertexGraph;

/* A vertex object with specified weight */
public class WeightedVertex {
	
	private final double x;
	private final double y;
	private final double weight;
	private WeightedVertexCycle cycle;
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWeight() {
		return weight;
	}

	public WeightedVertexCycle getCycle() {
		return cycle;
	}

	public void setCycle(WeightedVertexCycle cycle) {
		this.cycle = cycle;
	}

	public WeightedVertex(double x, double y, double weight) {
		super();
		this.x = x;
		this.y = y;
		this.weight = weight;
	}
	
	public String toString() {
		return getX() + "\t" + getY() + "\t" + getWeight();
	}
	
}
