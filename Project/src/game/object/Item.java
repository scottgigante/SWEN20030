package game.object;

import game.framework.Camera;
import game.framework.GameObject;
import game.framework.TextRenderer;
import game.framework.World;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Item extends GameObject {
	private static final long serialVersionUID = 1938266996286355149L;

	public Item(Vector2f pos, Image sprite, World world, String name, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, health, damage, cooldown);
	}
	
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
	
	@Override
	public void interact(GameObject o) {
		if (o instanceof Player) {
			((Player)o).addItem(this);
			destroy();
		}
	}

}
