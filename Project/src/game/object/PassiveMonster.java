package game.object;

import game.framework.GameObject;
import game.framework.World;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class PassiveMonster extends Monster {
	private static final long serialVersionUID = -7640845022173820464L;
	/* Passive monster's stats: does no damage */
	private static final int DAMAGE = 0;
	private static final int COOLDOWN = 0;
	private static final float MAX_SPEED = 0.2f;
	
	/** Amount of time to spend running away after an attack */
	private static final int FLEE_TIME = 5000;
	
	/** Time remaining to flee */
	private int currentFleeTime=0;
	
	/** Creates a new PassiveMonster object
	 * @param pos The monster's x, y position in pixels
	 * @param sprite The image representing the monster
	 * @param world The world in which the monster lives
	 * @param name The monster's name
	 * @param health The monster's max health
	 */
	public PassiveMonster(Vector2f pos, Image sprite, World world, String name, int health) {
		super(pos, sprite, world, name, MAX_SPEED, health, DAMAGE, COOLDOWN);
	}

	/* (non-Javadoc)
	 * Passive monster flees a player if it has been attacked recently
	 * @see game.object.Monster#see(game.object.Player)
	 */
	@Override
	protected Vector2f see(Player player) {
		if (currentFleeTime > 0) {
			return new Vector2f(getCenterX()-player.getCenterX(), getCenterY()-player.getCenterY());
		} else {
			return wander();
		}
	}
	
	/* (non-Javadoc)
	 * If the player is out of sight, no need to flee anymore - set flee time to zero
	 * @see game.object.Monster#wander()
	 */
	@Override
	public Vector2f wander() {
		if (currentFleeTime>0) {
			currentFleeTime = 0;
		}
		return super.wander();
	}
	
	/* (non-Javadoc)
	 * Interact with an attacker when being attacked
	 * @see game.object.Character#takeDamage(int, game.object.Character)
	 */
	@Override
	public boolean takeDamage(int damage, Character attacker) {
		interact(attacker);
		return super.takeDamage(damage, attacker);
	}

	/* (non-Javadoc)
	 * Flee an attacker
	 * @see game.framework.GameObject#interact(game.framework.GameObject)
	 */
	@Override
	public void interact(GameObject o) {
		currentFleeTime = FLEE_TIME;
	}

	/* (non-Javadoc)
	 * Update flee time
	 * @see game.object.Monster#update(game.object.Player, int)
	 */
	@Override
	public void update(Player player, int delta) {
		currentFleeTime-=delta;
		super.update(player, delta);
	}
}
