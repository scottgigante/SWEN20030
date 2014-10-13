package game.object;

import framework.Camera;
import framework.GameObject;
import framework.TextRenderer;
import framework.World;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class Item extends GameObject {
	private static final long serialVersionUID = 1938266996286355149L;

	/** Creates a new Item object
	 * @param pos The item's x, y position in pixels
	 * @param sprite The image representing the item
	 * @param world The world in which the item lives
	 * @param name The item's name
	 * @param health The health boost the item gives
	 * @param damage The damage boost the item gives
	 * @param cooldown The cooldown boost the item gives
	 */
	public Item(Vector2f pos, Image sprite, World world, String name, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, health, damage, cooldown);
	}
	
	/* (non-Javadoc)
	 * Render's the item's name above its image
	 * @see game.framework.GameObject#render(org.newdawn.slick.Graphics, game.framework.Camera)
	 */
	@Override
	public void render(Graphics g, Camera camera) {
		super.render(g, camera);
        
		if (camera.isOnScreen(this)) {	        
			TextRenderer.renderText(g, getCenterX()-camera.getMinX(), getMinY()-camera.getMinY(), 0, getName());
		}
	}
	
	/** Render the item at a particular point, regardless of its location
	 * @param x X position on screen to be rendered at
	 * @param y Y position on screen to be rendered at
	 */
	public void render(float x, float y) {
		getSprite().draw(x,y);
	}
	
	/* (non-Javadoc)
	 * Item is picked up by player, otherwise does nothing
	 * @see game.framework.GameObject#interact(game.framework.GameObject)
	 */
	@Override
	public void interact(GameObject o) {
		if (o instanceof Player) {
			((Player)o).addItem(this);
			destroy();
		}
	}

}
