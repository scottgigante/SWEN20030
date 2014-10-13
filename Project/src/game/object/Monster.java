package game.object;

import framework.World;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class Monster extends Character {
	private static final long serialVersionUID = 3035618347609591153L;
	/** Total length of monsters' sight line */
	private static int SEE_DISTANCE = 800;
	/** Amount of time between changing directions */
	private static int WANDER_TIME = 1000;
	/** Amount of time remaining between direction changes */
	private int currentWanderTime=0;
	/** Current direction */
	private Vector2f wanderDir;
	
	/** Creates a new monster object
	 * @param pos The monster's x, y position in pixels
	 * @param sprite The image representing the monster
	 * @param world The world in which the monster lives
	 * @param name The monster's name
	 * @param speed The monster's max speed
	 * @param health The monster's max health
	 * @param damage The monster's max damage
	 * @param cooldown The monster's max cooldown
	 */
	public Monster(Vector2f pos, Image sprite, World world, String name, float speed, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, speed, health, damage, cooldown);
		wanderDir = new Vector2f(0,0);
	}
	
	/** Sets current direction randomly
	 * @return A Vector2f describing direction to be followed
	 */
	protected Vector2f wander() {
		if (currentWanderTime <= 0) {
			double theta = Math.random()*Math.PI*2;
			wanderDir.x = (float)Math.cos(theta);
			wanderDir.y = (float)Math.sin(theta);
			currentWanderTime = WANDER_TIME;
		}
		return wanderDir;
	}
	
	/** Enact monster's behaviour upon seeing the player
	 * @param player The player that has been seen
	 * @return A Vector2f describing direction to be followed
	 */
	protected abstract Vector2f see(Player player);
	
	/** Update the monster's position based on whether or not it can see the player
	 * @param player The player to be seen
	 * @param delta The number of milliseconds since last update
	 */
	public void update(Player player, int delta) {
		// update wander time
		currentWanderTime -= delta;
		Vector2f dir;
		if (dist(player) < SEE_DISTANCE && world.hasLineOfSight(player, this)) {
			// player is close enough to be seen and has line of sight
			dir = see(player);
		} else {
			dir = wander();
		}
		// scale to size
		float len = dir.length();
		if (len != 0) {
			dir.x /= len;
			dir.y /= len;
		}
		super.update(dir, delta);
	}
}
