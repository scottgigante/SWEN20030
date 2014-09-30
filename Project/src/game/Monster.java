package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class Monster extends Character {
	
	/** Total length of monsters' sight line */
	private static int SEE_DISTANCE = 800;
	
	public Monster(Vector2f pos, Image sprite, World world, String name, float speed, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, speed, health, damage, cooldown);
		// TODO Auto-generated constructor stub
	}
	
	public Vector2f wander() {
		// TODO stub
		return new Vector2f(0,0);
	}
	
	public abstract Vector2f see(Player player);
	
	/** Update the monster's position based on whether or not it can see the player
	 * @param player The player to be seen
	 * @param delta The number of milliseconds since last update
	 */
	public void update(Player player, int delta) {
		Vector2f dir;
		if (dist(player) < SEE_DISTANCE && world.hasLineOfSight(player, this)) {
			// player is close enough to be seen and has line of sight
			dir = see(player);
		} else {
			dir = wander();
		}
		// scale to size
		float len = dir.length();
		dir.x /= len;
		dir.y /= len;
		super.update(dir.x, dir.y, delta);
	}
}
