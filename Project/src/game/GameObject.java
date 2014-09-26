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
	
	public void render(Graphics g) {
		// TODO stub
	}
	
	public void destroy() {
		// TODO stub
	}
	
	public abstract void interact(GameObject o);

}
