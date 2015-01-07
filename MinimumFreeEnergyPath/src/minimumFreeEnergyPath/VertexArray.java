package minimumFreeEnergyPath;

import java.util.ArrayList;

public class VertexArray extends ArrayList<WeightedVertex> {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Double> xHeader;
	private ArrayList<Double> yHeader;
	
	public void setXHeader(ArrayList<Double> xHeader) {
		this.xHeader = xHeader;
	}
	
	public void setYHeader(ArrayList<Double> yHeader) {
		this.yHeader = yHeader;
	}
	
	/** Finds the vertex at given coordinates, if it exists
	 * @return The vertex at (x,y), or null if there isn't one
	 */
	public WeightedVertex getAtCoords(double x, double y) {
		for (WeightedVertex v : this) {
			if (v.getX() == x && v.getY() == y) {
				return v;
			}
		}
		return null;
	}
	
	/** Finds the vertex at given weight, if it exists
	 * @return The vertex with given weight, or null if there isn't one
	 */
	public WeightedVertex getAtWeight(double weight) {
		for (WeightedVertex v : this) {
			if (v.getWeight() == weight) {
				return v;
			}
		}
		return null;
	}
	
	/** Checks if two vertices are adjacent
	 * @return True if they are adjacent, false otherwise
	 */
	public boolean isAdjacent(WeightedVertex v1, WeightedVertex v2) {
		return Math.abs(xHeader.indexOf(v1.getX()) - xHeader.indexOf(v2.getX())) <= 1 &&  Math.abs(yHeader.indexOf(v1.getY()) - yHeader.indexOf(v2.getY())) <= 1;
	}
}
