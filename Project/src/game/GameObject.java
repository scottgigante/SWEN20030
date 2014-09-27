package game;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public abstract class GameObject extends Rectangle {
	private int health;
	private int damage;
	private int cooldown;
	private Image sprite;
	/** The image representing the character, not flipped */
	private Image spriteNf;
	/** The image facing the opposite direction */
	private Image spriteF;
	private String name;
	
	/** Constructor creates a GameObject
	 * @param x The central x coordinate
	 * @param y The central y coordinate
	 * @param sprite Image of the object
	 */
	public GameObject(float x, float y, Image sprite, String name, int health, int damage, int cooldown) {
		super(x - sprite.getWidth()/2, y-sprite.getHeight()/2, sprite.getWidth(), sprite.getHeight());
		this.sprite = sprite;
		spriteNf = sprite;
		spriteF = sprite.getFlippedCopy(true, false);
		this.name = name;
		this.health = health;
		this.damage = damage;
		this.cooldown = cooldown;
		// TODO Auto-generated constructor stub
	}
	
	/* Getters and setters */	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getCooldown() {
		return cooldown;
	}
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Image getSprite() {
		return sprite;
	}
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	
	public Image getSpriteNf() {
		return spriteNf;
	}
	public Image getSpriteF() {
		return spriteF;
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
