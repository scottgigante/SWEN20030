package minimumFreeEnergyPath;

/* A vertex object with specified weight */
public class WeightedVertex {
	
	private final double x;
	private final double y;
	private final double weight;
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWeight() {
		return weight;
	}

	public WeightedVertex(double x, double y, double weight) {
		super();
		this.x = x;
		this.y = y;
		this.weight = weight;
	}
	
	public String toString() {
		return getX() + " " + getY() + " " + getWeight();
	}
	
}
