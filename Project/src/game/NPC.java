package game;

public abstract class NPC extends GameObject {

	private String[] dialogue;
	
	public NPC(float x, float y, float width, float height) {
		super(x, y, width, height);
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
