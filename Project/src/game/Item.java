package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class Item extends GameObject {

	public Item(float x, float y, Image sprite, String name, int health, int damage, int cooldown) {
		super(x, y, sprite, name, health, damage, cooldown);
		// TODO Auto-generated constructor stub
	}
	
	public void render(Graphics g) {
		// TODO stub
	}
	
	@Override
	public void interact(GameObject o) {
		// TODO Auto-generated method stub

	}

}
