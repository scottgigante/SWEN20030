import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.GameContainer;

public class GameObject {
	
	private Image sprite;
	private Vector2f centre;
	private float rotation = (float)-Math.PI/2;
	private float accel = 0.002f;
	private float rotate_speed = 0.004f;
	private float speed = 0f;
	private float max_speed = 0.5f;
	private GameContainer gc;
	
	public GameObject(GameContainer gc, Image sprite) {
		super();
		this.gc = gc;
		this.sprite = sprite;
	}
	public GameObject(GameContainer gc, Image sprite, Vector2f centre) {
		super();
		this.gc = gc;
		this.sprite = sprite;
		this.centre = centre;
	}
	public GameObject(GameContainer gc, Image sprite, Vector2f centre, float max_speed) {
		super();
		this.gc = gc;
		this.sprite = sprite;
		this.centre = centre;
		this.max_speed = max_speed;
	}
	public float getRotate_speed() {
		return rotate_speed;
	}
	public void setRotate_speed(float rotate_speed) {
		this.rotate_speed = rotate_speed;
	}
	public float getSpeed() {
		return speed;
	}
	public float getAccel() {
		return accel;
	}
	public void setAccel(float accel) {
		this.accel = accel;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public Image getSprite() {
		return sprite;
	}
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	public Vector2f getCentre() {
		return centre;
	}
	public void setCentre(Vector2f centre) {
		this.centre = centre;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void update() {
		if (speed > max_speed) {
			speed = max_speed;
		} else if (speed < -max_speed) {
			speed = -max_speed;
		}
		centre.x += Math.cos(rotation)*speed;
		centre.y += Math.sin(rotation)*speed;
		if (centre.getX() > gc.getWidth()) {
			centre.x -= gc.getWidth();
		} else if (centre.getX() < 0) {
			centre.x += gc.getWidth();
		}
		if (centre.getY() > gc.getHeight()) {
			centre.y -= gc.getHeight();
		} else if (centre.getY() < 0) {
			centre.y += gc.getHeight();
		}
		draw();
	}
	public void draw() {
		if (speed != 0) {
			sprite.setRotation((float)((rotation+Math.PI/2)*180/Math.PI));
		}
		sprite.draw(centre.getX(), centre.getY());
		if (centre.getX() > gc.getWidth() - sprite.getWidth()) {
			sprite.draw(centre.getX() - gc.getWidth(), centre.getY());
			if (centre.getY() > gc.getHeight() - sprite.getHeight()) {
				sprite.draw(centre.getX() - gc.getWidth(), centre.getY() - gc.getHeight());
			}	
		}
		if (centre.getY() > gc.getHeight() - sprite.getHeight()) {
			sprite.draw(centre.getX(), centre.getY() - gc.getHeight());
		}	
	}
}
