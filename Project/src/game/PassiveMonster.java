package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class PassiveMonster extends Monster {

	private static final int DAMAGE = 0;
	private static final int COOLDOWN = 0;
	private static final float MAX_SPEED = 0.2f;
	
	public PassiveMonster(Vector2f pos, Image sprite, World world, String name, int health) {
		super(pos, sprite, world, name, MAX_SPEED, health, DAMAGE, COOLDOWN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void see(Player player) {
		// TODO Auto-generated method stub
		
	}

}
