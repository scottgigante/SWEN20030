package game.object;

import game.framework.Camera;
import game.framework.GameObject;
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
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        
        String text = getName();
        int textWidth = g.getFont().getWidth(text);
        int barWidth = Math.max(textWidth+6, 70);
        int barHeight = g.getFont().getLineHeight();
		Rectangle bar = new Rectangle(getCenterX() - barWidth/2, getMinY()-barHeight*3/2, barWidth, barHeight);
		if (camera.isOnScreen(bar)) {	        
	        g.setColor(BAR_BG);
	        g.fillRect(bar.getMinX()-camera.getMinX(), bar.getMinY()-camera.getMinY(), bar.getWidth(), bar.getHeight());
	        g.setColor(VALUE);
	        g.drawString(getName(), getCenterX()-textWidth/2-camera.getMinX(), getMinY()-g.getFont().getLineHeight()*3/2-camera.getMinY());
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
