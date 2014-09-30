package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class PassiveMonster extends Monster {

	private static final int DAMAGE = 0;
	private static final int COOLDOWN = 0;
	private static final float MAX_SPEED = 0.2f;
	private boolean flee;
	
	public PassiveMonster(Vector2f pos, Image sprite, World world, String name, int health) {
		super(pos, sprite, world, name, MAX_SPEED, health, DAMAGE, COOLDOWN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector2f see(Player player) {
		// TODO Auto-generated method stub
		if (flee) {
			return new Vector2f(getCenterX()-player.getCenterX(), getCenterY()-player.getCenterY());
		} else {
			return wander();
		}
	}
	
	@Override
	public Vector2f wander() {
		if (flee) {
			flee = false;
		}
		return super.wander();
	}

}
