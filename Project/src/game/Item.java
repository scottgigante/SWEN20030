package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class Item extends GameObject {

	public Item(Vector2f pos, Image sprite, World world, String name, int health, int damage, int cooldown) {
		super(pos, sprite, world, name, health, damage, cooldown);
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
