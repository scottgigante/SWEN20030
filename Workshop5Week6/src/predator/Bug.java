package predator;


public class Bug extends Organism {
	private int hunger;
	public Bug(Grid grid) {
		super(grid);
		hunger = 0;
		representation = "X";
	}
	public Bug(Grid grid, int i, int j) {
		super(grid, i, j);
		hunger = 0;
		representation = "X";
	}
	
	public void update(Simulation sim) {
		int ant = sim.grid.findAnt(getI(), getJ());
		if (ant != -1) {
			sim.grid.moveToKill(getI(),getJ(),ant);
			hunger = 0;
		} else {
			hunger++;
			if (hunger >= Predator.BUG_DEATH_AGE) {
				kill(sim.grid);
			} else {
				super.update(sim);
			}
		}
		setAge(getAge()+1);
		if (getAge()>=Predator.BUG_BREED_AGE) {
			if (sim.addAdjacent(getI(), getJ()) != null) {
				setAge(0);
			}
		}
	}
}
