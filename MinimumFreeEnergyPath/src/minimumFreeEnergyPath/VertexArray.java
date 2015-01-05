package minimumFreeEnergyPath;

import java.util.ArrayList;

public class VertexArray extends ArrayList<Vertex> {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Double> xHeader;
	private ArrayList<Double> yHeader;
	
	public void setXHeader(ArrayList<Double> xHeader) {
		this.xHeader = xHeader;
	}
	
	public void setYHeader(ArrayList<Double> yHeader) {
		this.yHeader = yHeader;
	}
	
	/** Checks if the vertex fullfils hard coded requirement for the 'root node', in this case top right
	 * @param v The vertex to be checked
	 * @return True, it's the root. False, it's not.
	 */
	public boolean isRoot(Vertex v) {
		return v.getX() == xHeader.get(xHeader.size()-1) && v.getY() == yHeader.get(0);
	}
	
	public boolean isAdjacent(Vertex v1, Vertex v2) {
		return Math.abs(xHeader.indexOf(v1.getX()) - xHeader.indexOf(v2.getX())) <= 1 &&  Math.abs(yHeader.indexOf(v1.getY()) - yHeader.indexOf(v2.getY())) <= 1;
	}
}
