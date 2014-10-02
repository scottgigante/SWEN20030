package game.object;

import game.framework.GameObject;
import game.framework.World;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class AggressiveMonster extends Monster {
	private static final long serialVersionUID = 8814099892146069366L;
	private static final float MAX_SPEED = 0.25f;
	
	public AggressiveMonster(Vector2f pos, Image sprite, World world, String name, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, MAX_SPEED, health, damage, cooldown);
		// TODO Auto-generated constructor stub
	}
	
	public AggressiveMonster(Vector2f pos, Image sprite, World world, String name, float speed, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, speed, health, damage, cooldown);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector2f see(Player player) {
		return new Vector2f(player.getCenterX()-getCenterX(), player.getCenterY()-getCenterY());
	}


	@Override
	public void interact(GameObject o) {
		if (o instanceof Player) {
			attack((Player) o);
		}
	}
}
