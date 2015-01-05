package minimumFreeEnergyPath;

/* A vertex object with specified weight */
public class Vertex {
	
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

	public Vertex(double x, double y, double weight) {
		super();
		this.x = x;
		this.y = y;
		this.weight = weight;
	}
	
	
	
}
