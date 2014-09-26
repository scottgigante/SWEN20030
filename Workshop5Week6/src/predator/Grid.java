package predator;
import java.util.ArrayList;
import java.util.Collections;

public class Grid {
	private GridSquare[][] gs;
	private final int width;
	private final int height;
	protected int num_organisms;
	
	public Grid(int i, int j) {
		width = i;
		height = j;
		num_organisms = 0;
		gs = new GridSquare[i][j];
		for (int n=0;n<i;n++) {
			for (int m=0;m<j;m++) {
				gs[n][m] = new GridSquare();
			}
		}
	}
	
	public void add(Organism o) {
		if (num_organisms < width*height) {
			int i;
			int j;
			boolean success;
			do {
				i = (int)(Math.random()*width);
				j = (int)(Math.random()*height);
				success = gs[i][j].add(o);
			} while (!success);
			o.setI(i);
			o.setJ(j);
			num_organisms++;
		}
	}
	
	public Bug addBug(int i, int j) {
		ArrayList<Integer> dir_list = new ArrayList<Integer>();
		dir_list.add(0);
		dir_list.add(1);
		dir_list.add(2);
		dir_list.add(3);
		Collections.shuffle(dir_list);
		for (Integer dir : dir_list) {
			switch (dir) {
			case 0:
				if (j > 0 && !gs[i][j-1].hasOrganism()) {
					return new Bug(this, i, j-1);
				}
				break;
			case 1:
				if (j < height - 1 && !gs[i][j+1].hasOrganism()) {
					return new Bug(this, i, j+1);
				}
				break;
			case 2:
				if (i > 0 && !gs[i-1][j].hasOrganism()) {
					return new Bug(this, i-1, j);
				}
				break;
			case 3:
				if (i < width - 1 && !gs[i+1][j].hasOrganism()) {
					return new Bug(this, i+1, j);
				}
				break;
			}
		}
		return null;
	}
	public Ant addAnt(int i, int j) {
		ArrayList<Integer> dir_list = new ArrayList<Integer>();
		dir_list.add(0);
		dir_list.add(1);
		dir_list.add(2);
		dir_list.add(3);
		for (Integer dir : dir_list) {
			switch (dir) {
			case 0:
				if (j > 0 && !gs[i][j-1].hasOrganism()) {
					return new Ant(this, i, j-1);
				}
				break;
			case 1:
				if (j < height - 1 && !gs[i][j+1].hasOrganism()) {
					return new Ant(this, i, j+1);
				}
				break;
			case 2:
				if (i > 0 && !gs[i-1][j].hasOrganism()) {
					return new Ant(this, i-1, j);
				}
				break;
			case 3:
				if (i < width - 1 && !gs[i+1][j].hasOrganism()) {
					return new Ant(this, i+1, j);
				}
				break;
			}
		}
		return null;
	}
	
	public boolean add(Organism o, int i, int j) {
		if (i>=0 && i<width && j>=0 && j<height && !gs[i][j].hasOrganism()) {
			o.setI(i);
			o.setJ(j);
			num_organisms++;
			return gs[i][j].add(o);
		}
		return false;
	}
	
	public int findAnt(int i, int j) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		java.util.Collections.shuffle(list);
		for (Integer dir:list) {
			switch (dir) {
			case 0:
				// move up
				if (j > 0 && gs[i][j-1].hasAnt()) {
					return 0;
				}
				break;
			case 1:
				// move down
				if (j < height-1 && gs[i][j+1].hasAnt()) {
					return 1;
				}
				break;
			case 2:
				// move left
				if (i > 0 && gs[i-1][j].hasAnt()) {
					return 2;
				}
				break;
			case 3:
				// move right
				if (i < width-1 && gs[i+1][j].hasAnt()) {
					return 3;
				}
				break;
			}
		}
		return -1;
	}
	
	public void moveToKill(int i, int j, int dir) {
		switch (dir) {
		case 0:
			gs[i][j-1].kill(this);
			break;
		case 1:
			gs[i][j+1].kill(this);
			break;
		case 2:
			gs[i-1][j].kill(this);
			break;
		case 3:
			gs[i+1][j].kill(this);
			break;
		}
		move(i,j,dir);
	}
	
	public void move(int i, int j, int dir) {
		switch (dir) {
		case 0:
			// move up
			if (j > 0 && !gs[i][j-1].hasOrganism()) {
				Organism o = gs[i][j].remove();
				o.setJ(o.getJ()-1);
				gs[i][j-1].add(o);
			}
			break;
		case 1:
			// move down
			if (j < height-1 && !gs[i][j+1].hasOrganism()) {
				Organism o = gs[i][j].remove();
				o.setJ(o.getJ()+1);
				gs[i][j+1].add(o);
			}
			break;
		case 2:
			// move left
			if (i > 0 && !gs[i-1][j].hasOrganism()) {
				Organism o = gs[i][j].remove();
				o.setI(o.getI()-1);
				gs[i-1][j].add(o);
			}
			break;
		case 3:
			// move right
			if (i < width-1 && !gs[i+1][j].hasOrganism()) {
				Organism o = gs[i][j].remove();
				o.setI(o.getI()+1);
				gs[i+1][j].add(o);
			}
			break;
		}
	}
	
	public void draw() {
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				gs[j][i].draw();
			}
			System.out.printf("\n");
		}
	}
}
