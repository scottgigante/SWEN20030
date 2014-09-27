package game;

import org.newdawn.slick.Image;

public abstract class Monster extends Character {

	public Monster(int x, int y, float speed, Image sprite, World world) {
		super(x, y, speed, sprite, world);
		// TODO Auto-generated constructor stub
	}
	
	public void wander() {
		// TODO stub
	}
	
	public abstract void see(Player player);
}
