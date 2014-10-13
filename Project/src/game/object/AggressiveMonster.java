package game.object;

import framework.GameObject;
import framework.World;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class AggressiveMonster extends Monster {
	private static final long serialVersionUID = 8814099892146069366L;
	private static final float MAX_SPEED = 0.25f;

	/** Creates a new AggressiveMonster object with default speed
	 * @param pos The monster's x, y position in pixels
	 * @param sprite The image representing the monster
	 * @param world The world in which the monster lives
	 * @param name The monster's name
	 * @param health The monster's max health
	 * @param damage The monster's max damage
	 * @param cooldown The monster's max cooldown
	 */
	public AggressiveMonster(Vector2f pos, Image sprite, World world, String name, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, MAX_SPEED, health, damage, cooldown);
		// TODO Auto-generated constructor stub
	}
	
	/** Creates a new AggressiveMonster object with custom speed
	 * @param pos The monster's x, y position in pixels
	 * @param sprite The image representing the monster
	 * @param world The world in which the monster lives
	 * @param name The monster's name
	 * @param speed The monster's max speed
	 * @param health The monster's max health
	 * @param damage The monster's max damage
	 * @param cooldown The monster's max cooldown
	 */
	public AggressiveMonster(Vector2f pos, Image sprite, World world, String name, float speed, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, speed, health, damage, cooldown);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * Chase a player when it is in sight
	 * @see game.object.Monster#see(game.object.Player)
	 */
	@Override
	protected Vector2f see(Player player) {
		return new Vector2f(player.getCenterX()-getCenterX(), player.getCenterY()-getCenterY());
	}


	/* (non-Javadoc)
	 * Attack a player when it is in reach
	 * @see game.framework.GameObject#interact(game.framework.GameObject)
	 */
	@Override
	public void interact(GameObject o) {
		if (o instanceof Player) {
			attack((Player) o);
		}
	}
}
