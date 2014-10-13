package framework;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class Path {
	/** Horizontal cost of movement */
	private static final int H_COST = 10;
	/** Diagonal cost of movement */
	private static final int D_COST = 14;
	/** Tolerance, in pixels, which will be accepted in a radius around the destination of a path */
	private static final double PATH_TOLERANCE = 5;
	
	/** 2d list of nodes for easy construction of paths */
	private Node[][] nodeList;
	
	/** The path currently being followed, if any */
	private ArrayList<Vector2f> path;
	/** the map upon which paths are constructed */
	private Map map;
	
	/** Constructor creates a new path object */
	public Path(World world) {
		path = new ArrayList<Vector2f>();
		this.map = world.getMap();
	}
	
	/** Adds a node to the start of a path
	 * @param pos Vector2f representing the x and y coords of node
	 */
	private void addAtStart(Vector2f pos) {
		path.add(0, pos);
	}

	/** Adds a node to the end of a path
	 * @param pos Vector2f representing the x and y coords of node
	 */
	private void addAtEnd(Vector2f pos) {
		path.add(pos);
	}
	
	/** Checks if there is currently apath ot be followed
	 * @return True or false.
	 */
	public boolean hasPath() {
		return !path.isEmpty();
	}
	
	/** Checks the direction to be followed to arrive at the next node from a position
	 * @param pos The current position
	 * @return Vector2f representing the direction to be followed
	 */
	public Vector2f getDir(Vector2f pos) {
		Vector2f dir = new Vector2f(0,0);
		// Use PATH_TOLERANCE/2 to avoid having to use sqrt(2)
		if (pos.x < current().x-PATH_TOLERANCE/2) {
			dir.x = 1;
		} else if (pos.x > current().x+PATH_TOLERANCE/2) {
			dir.x = -1;
		}
		if (pos.y < current().y-PATH_TOLERANCE/2) {
			dir.y = 1;
		} else if (pos.y > current().y+PATH_TOLERANCE/2) {
			dir.y = -1;
		}		
		return dir;
	}
	
	/** Checks if the next node on the path has been reached
	 * @param pos The current position
	 */
	public void update(Vector2f pos) {
		if (!path.isEmpty()) {
			if (current().distance(pos) < PATH_TOLERANCE) {
				next();
			}
		}
	}
	
	/** Moves to the next node on the path */
	private void next() {
		path.remove(0);
	}
	
	/** Clears the current path */
	public void clear() {
		path.clear();
	}
	
	/** Returns the current node
	 * @return Vector2f of current node's position
	 */
	private Vector2f current() {
		return path.get(0);
	}
	
	/** Find a path from one map tile another using A* algorithm
	 * @param start A Vector2f representing the position to start the path, in pixels
	 * @param stop Vector2f representing path's end, in pixels
	 * @return An array of Vector2f as the path of tiles to visit
	 */
	public void setPath(Vector2f start, Vector2f stop) {
		// initial check that the destination tile is walkable
		if (!map.isWalkable(stop.x, stop.y)) {
			return;
		}
		
		if (nodeList == null) {
			// haven't yet initialised - init here to avoid init for characters that don't use pathfinding
			nodeList = new Node[map.getWidth()][map.getHeight()];
			for (int i = 0; i<map.getWidth(); i++) {
				for (int j = 0; j<map.getHeight(); j++) {
					nodeList[i][j] = new Node(i,j);
				}
			}
		}
		
		ArrayList<Node> closed = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		// modify start and stop positions to refer to a single tile
		Vector2f start_block = new Vector2f((int)(start.x/map.getTileWidth()),(int)(start.y/map.getTileHeight()));
		Vector2f stop_block = new Vector2f((int)(stop.x/map.getTileWidth()),(int)(stop.y/map.getTileHeight()));
		
		// Add the starting node to the closed list
		Node current = nodeList[(int)start_block.x][(int)start_block.y];
		Node next;		
		nodeList[(int)start_block.x][(int)start_block.y].reset();
		closed.add(nodeList[(int)start_block.x][(int)start_block.y]);
		
		
		while (!closed.contains(nodeList[(int)stop_block.x][(int)stop_block.y])) {
			// Update all surrounding nodes to the open list
			if (current.getX() > 0) {
				// start with square to the left
				next = nodeList[current.getX()-1][current.getY()];
				if (map.isWalkable(next.getX(),next.getY())) {
					next.update(open, closed, current, H_COST, stop_block);
					if (current.getY() > 0) {
						// up and to the left
						next = nodeList[current.getX()-1][current.getY()-1];
						if (map.isWalkable(next.getX(),next.getY())) {
							next.update(open, closed, current, D_COST,stop_block);
						}
					}
					if (current.getY() < map.getHeight()-1) {
						// down and to the left
						next = nodeList[current.getX()-1][current.getY()+1];
						if (map.isWalkable(next.getX(),next.getY())) {
							next.update(open, closed, current, D_COST,stop_block);
						}
					}
				}
			}
			if (current.getX() < map.getWidth()-1) {
				// to the right
				next = nodeList[current.getX()+1][current.getY()];
				if (map.isWalkable(next.getX(),next.getY())) {
					next.update(open, closed, current, H_COST, stop_block);
					if (current.getY() > 0) {
						// up and to the right
						next = nodeList[current.getX()+1][current.getY()-1];
						if (map.isWalkable(next.getX(),next.getY())) {
							next.update(open, closed, current, D_COST,stop_block);
						}
					}
					if (current.getY() < map.getHeight()-1) {
						// down and to the right
						next = nodeList[current.getX()+1][current.getY()+1];
						if (map.isWalkable(next.getX(),next.getY())) {
							next.update(open, closed, current, D_COST,stop_block);
						}
					}
				}
			}
			if (current.getY() > 0) {
				// up
				next = nodeList[current.getX()][current.getY()-1];
				if (map.isWalkable(next.getX(),next.getY())) {
					next.update(open, closed, current, H_COST, stop_block);
				}
			}
			if (current.getY() < map.getHeight()-1) {
				// down
				next = nodeList[current.getX()][current.getY()+1];
				if (map.isWalkable(next.getX(),next.getY())) {
					next.update(open, closed, current, H_COST, stop_block);
				}
			}
			
			// find the node with lowest F value
			int lowest = -1;
			for (Node node : open) {
				if (lowest == -1 || node.getF() < lowest) {
					lowest = node.getF();
					current = node;
				}
			}
			if (lowest == -1) {
				// open list is empty, path cannot be found
				return;
			}
			open.remove(current);
			closed.add(current);
		}
		
		// found the best path, now to return it
		// skip first and last tiles, don't need to actually go to them
		path.clear();
		current = current.getParent();
		while (current != null && current.getParent() != null) {
			addAtStart(new Vector2f((float)((current.getX()+0.5)*map.getTileWidth()),(float)((current.getY()+0.5)*map.getTileHeight())));
			current = current.getParent();
		}
		// finally, reach the actual point clicked rather than the corresponding grid square
		addAtEnd(stop);
	}
    
    private class Node {
		/** Position as a vector */
		private Vector2f pos;
		/** Cost to reach this node */
		private int G;
		/** Heuristic cost from here to path's end */
		private int H;
		/** Parent node from which we arrived */
		private Node parent;
		
		/** Constructor for a Node */
		public Node(int x, int y) {
			pos = new Vector2f(x,y);
		}
		
		/** Set all variables to zero when reaching a node for the first time */
		public void reset() {
			G=H=0;
			parent = null;
		}
		
		public int getX() {
			return (int)pos.x;
		}
		public int getY() {
			return (int)pos.y;
		}

		public int getG() {
			return G;
		}

		/** Calculate F, the combined cost so far and heuristic cost to go */
		public int getF() {
			return G+H;
		}

		public Node getParent() {
			return parent;
		}
		
		/** Finds the direction taken to reach this node from its parent node
		 * @return A Vector2f (x,y) of change in position
		 */
		private Vector2f getDir() {
			if (parent == null) {
				return new Vector2f(0,0);
			}
			return new Vector2f(getX()-parent.getX(), getY()-parent.getY());
		}

		/** Finds the direction taken to reach this node from a potential parent node
		 * @return A Vector2f (x,y) of change in position
		 */
		private Vector2f getDir(Node parent) {
			if (parent == null) {
				return new Vector2f(0,0);
			}
			return new Vector2f(getX()-parent.getX(), getY()-parent.getY());
		}
		
		/** Update variables (if necessary) for a new node
		 * @param open The open list
		 * @param closed The closed list
		 * @param current The node from which we are visiting
		 * @param next The node we are visiting
		 * @param cost The cost of moving from current to next
		 * @param stop The final goal node, used to calculate heuristics
		 */
		private void update(ArrayList<Node> open, ArrayList<Node> closed, Node current, int cost, Vector2f stop) {
			// If it is in the closed list, ignore it.
			if (!closed.contains(this)) {
				 // If it is not on the open list, add it to the open list.
				if (!open.contains(this)) {
					reset();
					open.add(this);
				}
				// add penalty to changing direction for 'smoother' path
				if (getDir(current) != current.getDir()) {
					cost += 1;
				}
				// If it is in the open list, check if we can reach it with a shorter cost.
				if (G == 0 || current.getG()+cost < G) {
					// Update the parent and cost variables.
					G = current.getG()+cost;
					H = (Math.abs(getX() - (int)stop.x)+Math.abs(getY()-(int)stop.y))*H_COST;
					parent = current;
				}
			}
		}
	}
}
