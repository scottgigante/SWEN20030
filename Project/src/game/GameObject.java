package game;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public abstract class GameObject extends Rectangle {
	private int health;
	private int damage;
	private int cooldown;
	private Image sprite;
	private String name;
	
	public GameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	/** Render an object within the scope of the camera
     * @param camera The viewport in which to draw
     */
    public void render(Camera camera) {
    	if (camera.isOnScreen(this)) {
    		// character is on-screen, draw it
    		sprite.draw(getMinX()-camera.getMinX(),getMinY()-camera.getMinY());
    	}
    }
	
	public void destroy() {
		// TODO stub
	}
	
	public abstract void interact(GameObject o);

}
