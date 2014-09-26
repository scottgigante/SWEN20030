package predator;

public class GridSquare {
	private Organism contained;
	
	public GridSquare() {
		contained = null;
	}
	public boolean hasOrganism() {
		if (contained != null) {
			if (contained.isDead()) {
				contained = null;
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	public boolean hasAnt() {
		return hasOrganism() && contained instanceof Ant;
	}
	public boolean add(Organism o) {
		if (!hasOrganism()) {
			contained = o;
			return true;
		} else {
			return false;
		}
	}
	public Organism remove() {
		Organism temp = contained;
		contained = null;
		return temp;
	}
	public void kill(Grid grid) {
		if (hasOrganism()) {
			contained.kill(grid);
			contained = null;
		}
	}
	public void draw() {
		if (hasOrganism()) {
			System.out.printf("%s ", contained.representation);
		} else {
			System.out.printf("  ");
		}
	}
}
