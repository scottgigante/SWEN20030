package minimumFreeEnergyPath.core;

import java.util.ArrayList;

import minimumFreeEnergyPath.weightedVertexGraph.WeightedVertex;

/** Helper class used in creation of a WeightedVertexGraph
 * @author Scott Gigante
 * @since Jan 2015
 */
public class VertexArray extends ArrayList<WeightedVertex> {
	private static final long serialVersionUID = 1L;
	
	/** List of column names */
	private ArrayList<Double> xHeader;
	/** List of row names */
	private ArrayList<Double> yHeader;
	
	// Getters and setters
	protected void setXHeader(ArrayList<Double> xHeader) {
		this.xHeader = xHeader;
	}
	
	protected void setYHeader(ArrayList<Double> yHeader) {
		this.yHeader = yHeader;
	}
	
	/** Finds the vertex at given coordinates, if it exists
	 * @param x The x coordinate to be retrieved
	 * @param y The y coordinate to be retrieved
	 * @return The vertex at (x,y), or null if there isn't one
	 */
	protected WeightedVertex getAtCoords(double x, double y) {
		for (WeightedVertex v : this) {
			if (v.getX() == x && v.getY() == y) {
				return v;
			}
		}
		return null;
	}
	
	/** Finds the vertex at given weight, if it exists
	 * @param weight The weight to be retrieved
	 * @return The vertex with given weight, or null if there isn't one
	 */
	protected WeightedVertex getAtWeight(double weight) {
		for (WeightedVertex v : this) {
			if (v.getWeight() == weight) {
				return v;
			}
		}
		return null;
	}
	
	/** Checks if two vertices are adjacent, assuming diagonal adjacency is allowed
	 * @param v1 The first vertex to be checked
	 * @param v2 The second vertex to be checked
	 * @return True if they are adjacent, false otherwise
	 */
	protected boolean isAdjacent(WeightedVertex v1, WeightedVertex v2) {
		return Math.abs(xHeader.indexOf(v1.getX()) - xHeader.indexOf(v2.getX())) <= 1 &&  Math.abs(yHeader.indexOf(v1.getY()) - yHeader.indexOf(v2.getY())) <= 1;
	}
}
