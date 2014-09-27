package game;

import org.newdawn.slick.Image;

public abstract class Monster extends Character {

	public Monster(int x, int y, Image sprite, World world, String name, float speed, int health, int damage, int cooldown) {
		super(x, y, sprite, world, name, speed, health, damage, cooldown);
		// TODO Auto-generated constructor stub
	}
	
	public void wander() {
		// TODO stub
	}
	
	public abstract void see(Player player);
}
