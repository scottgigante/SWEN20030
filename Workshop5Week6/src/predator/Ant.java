package predator;

public class Ant extends Organism {
	public Ant(Grid grid) {
		super(grid);
		representation = "o";
	}
	public Ant(Grid grid, int i, int j) {
		super(grid, i, j);
		representation = "o";
	}
	public void update(Simulation sim) {
		super.update(sim);
		setAge(getAge()+1);
		if (getAge() >= Predator.ANT_BREED_AGE) {
			setAge(0);
			sim.addAnts(getI(), getJ());
		}
	}
}
