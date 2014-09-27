package game;

import org.newdawn.slick.Image;

public abstract class NPC extends GameObject {

	private static int HEALTH = 1;
	private static int DAMAGE = 0;
	private static int COOLDOWN = 0;
	
	private String[] dialogue;
	
	public NPC(float x, float y, Image sprite, String name) {
		super(x, y, sprite, name, HEALTH, DAMAGE, COOLDOWN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interact(GameObject o) {
		// TODO Auto-generated method stub

	}
	
	public void speak(int line) {
		// TODO stub
	}

}
