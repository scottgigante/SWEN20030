package predator;

public class Organism {
	protected String representation;
	private int i;
	private int j;
	private int age;
	private boolean dead = false;
	
	public Organism(Grid grid) {
		age = 0;
		grid.add(this);
	}
	public Organism(Grid grid, int i, int j) {
		age = 0;
		grid.add(this, i, j);
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void update(Simulation sim) {
		int dir = (int)(Math.random()*4);
		sim.grid.move(i, j, dir);
	}
	public void kill(Grid grid) {
		dead = true;
		grid.num_organisms--;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	public boolean isDead() {
		return dead;
	}
}
