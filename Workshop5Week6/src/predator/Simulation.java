package predator;
import java.util.ArrayList;

public class Simulation {
	static final int size = 20;
	static final int init_ants = 100;
	static final int init_bugs = 5;
	
	protected Grid grid;
	ArrayList<Organism> organisms;

			
	
	public Simulation() {
		grid = new Grid(size,size);
		organisms = new ArrayList<Organism>(init_bugs+init_ants);
		for (int i = 0; i < init_bugs; i++) {
			organisms.add(new Bug(grid));
		}
		for (int i = 0; i < init_ants; i++) {
			organisms.add(new Ant(grid));
		}
		grid.draw();
	}
	
	public void add(Organism o) {
		organisms.add(o);
	}
	public Bug addAdjacent(int i, int j) {
		Bug bug = grid.addBug(i,j);
		if (bug != null) {
			organisms.add(bug);
		}
		return bug;
	}
	public void addAnts(int i, int j) {
		Ant ant = grid.addAnt(i,j);
		while (ant != null) {
			organisms.add(ant);
			ant = grid.addAnt(i,j);
		}
	}
	
	public boolean update() {
		boolean bugs_left = false;
		boolean ants_left = false;
		for(int i=organisms.size()-1;i>=0;i--){
			Organism o = organisms.get(i);
			if (o instanceof Bug) {
				o.update(this);
				if (o.isDead()) {
					organisms.remove(o);
				} else {
					bugs_left = true;
				}
			}
		}
		for(int i=organisms.size()-1;i>=0;i--){
			Organism o = organisms.get(i);
			if (o instanceof Ant) {
				if (o.isDead()) {
					organisms.remove(o);
				} else {
					o.update(this);
					ants_left = true;
				}
			}
		}
		grid.draw();
		return bugs_left && ants_left;
	}
}
