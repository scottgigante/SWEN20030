package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class PassiveMonster extends Monster {
	private static final long serialVersionUID = -7640845022173820464L;
	private static final int DAMAGE = 0;
	private static final int COOLDOWN = 0;
	private static final float MAX_SPEED = 0.2f;
	
	private static final int FLEE_TIME = 5000;
	
	private int currentFleeTime=0;
	
	public PassiveMonster(Vector2f pos, Image sprite, World world, String name, int health) {
		super(pos, sprite, world, name, MAX_SPEED, health, DAMAGE, COOLDOWN);
	}

	@Override
	public Vector2f see(Player player) {
		if (currentFleeTime > 0) {
			return new Vector2f(getCenterX()-player.getCenterX(), getCenterY()-player.getCenterY());
		} else {
			return wander();
		}
	}
	
	@Override
	public Vector2f wander() {
		if (currentFleeTime>0) {
			currentFleeTime = 0;
		}
		return super.wander();
	}
	
	@Override
	public void takeDamage(int damage, Character attacker) {
		interact(attacker);
		super.takeDamage(damage, attacker);
	}

	@Override
	public void interact(GameObject o) {
		currentFleeTime = FLEE_TIME;
	}

	@Override
	public void update(Player player, int delta) {
		currentFleeTime-=delta;
		super.update(player, delta);
	}
}
