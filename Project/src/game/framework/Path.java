package game.framework;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class Path {
	/** Tolerance, relative to speed, which will be accepted in a radius around the destination of a path */
	private static final double PATH_TOLERANCE = 5;
	
	/** The path currently being followed, if any */
	private ArrayList<Vector2f> path;
	
	public Path() {
		path = new ArrayList<Vector2f>();
	}
	
	public void addAtStart(Vector2f pos) {
		path.add(0, pos);
	}
	
	public void addAtEnd(Vector2f pos) {
		path.add(pos);
	}
	
	public boolean hasPath() {
		return !path.isEmpty();
	}
	
	public int length() {
		return path.size();
	}
	
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
	
	public void update(Vector2f pos) {
		if (!path.isEmpty()) {
			if (current().distance(pos) < PATH_TOLERANCE) {
				next();
			}
		}
	}
	
	public void next() {
		path.remove(0);
	}
	
	public void clear() {
		path.clear();
	}
	
	private Vector2f current() {
		return path.get(0);
	}
}
