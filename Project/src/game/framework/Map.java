/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game.framework;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

public class Map extends TiledMap {
	/** The location of the file containing map data */
	private static final String MAP_LOC = "assets/map.tmx";
	/** The location of the resources relating to the map */
	private static final String RESOURCE_LOC = "assets";
	/** Horizontal cost of movement */
	public static final int H_COST = 10;
	/** Diagonal cost of movement */
	public static final int D_COST = 14;
	
	/** 2d list of nodes for easy construction of paths */
	private Node[][] node_list;
	
	/** Constructor creates a new Map object */
	public Map() throws SlickException {
		super(MAP_LOC, RESOURCE_LOC);
		node_list = new Node[getWidth()][getHeight()];
		for (int i = 0; i<getWidth(); i++) {
			for (int j = 0; j<getHeight(); j++) {
				node_list[i][j] = new Node(i,j);
			}
		}
	}
	
	public int getPixelWidth() {
		return getWidth()*getTileWidth();
	}
	public int getPixelHeight() {
		return getHeight()*getTileHeight();
	}
	/** Checks if the tile at x, y is walkable or not
	 * @param x Integer representing tile grid position
	 * @param y Integer representing tile grid position
	 * @return True, tile is walkable. False, tile is not walkable.
	 */
	private boolean isWalkable(int x, int y) {
		return getTileProperty(getTileId(x, y, 0), "block", "0") == "0";
	}
	
	/** Checks if the tile at x, y is walkable or not
	 * @param x Float representing map position
	 * @param y Float representing map position
	 * @return True, tile is walkable. False, tile is not walkable.
	 */
	private boolean isWalkable(float x, float y) {
		return isWalkable((int)(x/getTileWidth()), (int)(y/getTileHeight()));
	}
	
	/** Check all four corners of a rectangle moving a particular distance are walkable
	 * and if the rectangle will still be on the map
	 * @param rect The rectangle to be moved
	 * @param x Float to be moved in x direction
	 * @param y float to be moved in y direction
	 * @return True, rectangle can move legally. False, it cannot
	 */
	public boolean canMove(Rectangle rect, float x, float y) {
		try {
			if (rect.getMinX() + x < 0 || rect.getMaxX() + x > getPixelWidth() || rect.getMinY()+y < 0 || rect.getMaxY() + y > getPixelHeight()) {
				// walking off the map
				return false;
			}
	
	    	// Check for terrain blocking
			if (!isWalkable(rect.getMinX()+x, rect.getMinY()+y) || !isWalkable(rect.getMaxX()+x, rect.getMaxY()+y) || !isWalkable(rect.getMinX()+x,rect.getMaxY()+y) || !isWalkable(rect.getMaxX()+x, rect.getMinY()+y)) {
				// any one of the four corners is unwalkable
				return false;
			}
			
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	/** Renders a tiled map behind within the constraints of the camera's view.
	 * @param camera The viewport in which to draw
     */
    public void render(Camera camera) {
    	// Calculate which tiles to start drawing from
    	int sx = camera.getMinX() / getTileWidth();
    	int sy = camera.getMinY() / getTileHeight();
    	// Calculate the overhang off the edge of the screen
    	int x = sx * getTileWidth() - camera.getMinX();
    	int y = sy * getTileHeight() - camera.getMinY();
    	super.render(x, y, sx, sy, camera.getScreenWidth(), camera.getScreenHeight());
    } 
	
	/** Find a path from one map tile another using A* algorithm
	 * @param start A Vector2f representing the position to start the path, in pixels
	 * @param stop Vector2f representing path's end, in pixels
	 * @return An array of Vector2f as the path of tiles to visit
	 */
	public Path findPath(Vector2f start, Vector2f stop) {
		Path path = new Path();
		// initial check that the destination tile is walkable
		if (!isWalkable(stop.x, stop.y)) {
			return path;
		}
		
		ArrayList<Node> closed = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		// modify start and stop positions to refer to a single tile
		Vector2f start_block = new Vector2f((int)(start.x/getTileWidth()),(int)(start.y/getTileHeight()));
		Vector2f stop_block = new Vector2f((int)(stop.x/getTileWidth()),(int)(stop.y/getTileHeight()));
		
		// Add the starting node to the closed list
		Node current = node_list[(int)start_block.x][(int)start_block.y];
		Node next;		
		node_list[(int)start_block.x][(int)start_block.y].reset();
		closed.add(node_list[(int)start_block.x][(int)start_block.y]);
		
		
		while (!closed.contains(node_list[(int)stop_block.x][(int)stop_block.y])) {
			// Update all surrounding nodes to the open list
			if (current.getX() > 0) {
				// start with square to the left
				next = node_list[current.getX()-1][current.getY()];
				if (isWalkable(next.getX(),next.getY())) {
					next.update(open, closed, current, H_COST, stop_block);
					if (current.getY() > 0) {
						// up and to the left
						next = node_list[current.getX()-1][current.getY()-1];
						if (isWalkable(next.getX(),next.getY())) {
							next.update(open, closed, current, D_COST,stop_block);
						}
					}
					if (current.getY() < getHeight()-1) {
						// down and to the left
						next = node_list[current.getX()-1][current.getY()+1];
						if (isWalkable(next.getX(),next.getY())) {
							next.update(open, closed, current, D_COST,stop_block);
						}
					}
				}
			}
			if (current.getX() < getWidth()-1) {
				// to the right
				next = node_list[current.getX()+1][current.getY()];
				if (isWalkable(next.getX(),next.getY())) {
					next.update(open, closed, current, H_COST, stop_block);
					if (current.getY() > 0) {
						// up and to the right
						next = node_list[current.getX()+1][current.getY()-1];
						if (isWalkable(next.getX(),next.getY())) {
							next.update(open, closed, current, D_COST,stop_block);
						}
					}
					if (current.getY() < getHeight()-1) {
						// down and to the right
						next = node_list[current.getX()+1][current.getY()+1];
						if (isWalkable(next.getX(),next.getY())) {
							next.update(open, closed, current, D_COST,stop_block);
						}
					}
				}
			}
			if (current.getY() > 0) {
				// up
				next = node_list[current.getX()][current.getY()-1];
				if (isWalkable(next.getX(),next.getY())) {
					next.update(open, closed, current, H_COST, stop_block);
				}
			}
			if (current.getY() < getHeight()-1) {
				// down
				next = node_list[current.getX()][current.getY()+1];
				if (isWalkable(next.getX(),next.getY())) {
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
				return path;
			}
			open.remove(current);
			closed.add(current);
		}
		
		// found the best path, now to return it
		// skip first and last tiles, don't need to actually go to them
		current = current.getParent();
		while (current != null && current != node_list[(int)start_block.x][(int)start_block.y]) {
			path.addAtStart(new Vector2f((float)((current.getX()+0.5)*getTileWidth()),(float)((current.getY()+0.5)*getTileHeight())));
			current = current.getParent();
		}
		// finally, reach the actual point clicked rather than the corresponding grid square
		path.addAtEnd(stop);
		return path;
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


