package game.framework;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Graphics;

public abstract class GameObject extends Rectangle {
	private static final long serialVersionUID = -7779997954127083405L;
	/* Object's statistics */
	private int health;
	private int damage;
	private int cooldown;
	/** Object's name */
	private String name;
	/** Current image representing the character, flipped or unflipped */
	private Image sprite;
	/** The world in which the character exists */
	protected World world;
	/** The image representing the character, not flipped */
	private Image spriteNf;
	/** The image facing the opposite direction */
	private Image spriteF;
	
	/** Constructor creates a GameObject
	 * @param pos The central coordinates, in x and y
	 * @param sprite Image of the object
	 * @param world World in which object lives
	 * @param name Object's name
	 * @param health Object's max health
	 * @param damage Object's max damage
	 * @param cooldown Object's max cooldown
	 */
	public GameObject(Vector2f pos, Image sprite, World world, String name, int health, int damage, int cooldown) {
		// Super constructor takes arguments to uppermost and rightmost coords, not centre
		super(pos.x - sprite.getWidth()/2.0f, pos.y-sprite.getHeight()/2.0f, sprite.getWidth(), sprite.getHeight());
		this.sprite = sprite;
		spriteNf = sprite;
		spriteF = sprite.getFlippedCopy(true, false);
		this.world = world;
		this.name = name;
		this.health = health;
		this.damage = damage;
		this.cooldown = cooldown;
	}
	
	/* Getters and setters */	
	public int getHealth() {
		return health;
	}
	protected void setHealth(int health) {
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

	protected String getName() {
		return name;
	}

	protected Image getSprite() {
		return sprite;
	}
	protected void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	
	protected Image getSpriteNf() {
		return spriteNf;
	}
	protected Image getSpriteF() {
		return spriteF;
	}
	
	public Vector2f getVectorCenter() {
		return new Vector2f(getCenterX(), getCenterY());
	}

	/** Render an object within the scope of the camera
     * @param camera The viewport in which to draw
     */
	protected void render(Graphics g, Camera camera) {
    	if (camera.isOnScreen(this)) {
    		// character is on-screen, draw it
    		sprite.draw(getMinX()-camera.getMinX(),getMinY()-camera.getMinY());
    	}
    }
	
	/** Object removes itself from the world and disappears */
	protected void destroy() {
		world.removeObject(this);
	}
	
	/** Checks if an object is within a certain distance of another, for interaction purposes
	 * @param o The GameObject to be checked
	 * @return True or false.
	 */
	protected double dist(GameObject o) {
		return Math.sqrt(Math.pow(Math.max(Math.abs(getCenterX()-o.getCenterX())-getBoundingCircleRadius()-o.getBoundingCircleRadius(),0), 2) + Math.pow(Math.max(Math.abs(getCenterY()-o.getCenterY())-getBoundingCircleRadius()-o.getBoundingCircleRadius(),0), 2));
	}
	
	public abstract void interact(GameObject o);

}
