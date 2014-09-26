package game;

import org.newdawn.slick.Image;

public abstract class Monster extends Character {

	public Monster(int x, int y, float speed, Image sprite, Map map) {
		super(x, y, speed, sprite, map);
		// TODO Auto-generated constructor stub
	}
	
	public void wander() {
		// TODO stub
	}
	
	public abstract void see(Player player);
}
