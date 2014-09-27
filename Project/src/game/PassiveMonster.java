package game;

import org.newdawn.slick.Image;

public abstract class PassiveMonster extends Monster {

	public PassiveMonster(int x, int y, Image sprite, World world, String name, float speed, int health, int damage, int cooldown) {
		super(x, y, sprite, world, name, speed, health, damage, cooldown);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void see(Player player) {
		// TODO Auto-generated method stub
		
	}

}
