package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class Monster extends Character {
	
	public Monster(Vector2f pos, Image sprite, World world, String name, float speed, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, speed, health, damage, cooldown);
		// TODO Auto-generated constructor stub
	}
	
	public void wander() {
		// TODO stub
	}
	
	public abstract void see(Player player);
}
