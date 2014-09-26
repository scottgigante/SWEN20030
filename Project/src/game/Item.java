package game;

import org.newdawn.slick.Graphics;

public abstract class Item extends GameObject {

	public Item(float x, float y, float width, float height) {
		super(x, y, width, height);
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
