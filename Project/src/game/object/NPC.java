package game.object;

import game.framework.Camera;
import game.framework.FileReader;
import game.framework.TextRenderer;
import game.framework.World;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class NPC extends Character {
	private static final long serialVersionUID = -1382699989591165743L;
	/** Amount of time for dialogue to be displayed, in milliseconds */
	private static final int SPEAK_TIME = 3000;
	
	/* NPC stats: health 1 to show red name bar, no other actions */
	private static int HEALTH = 1;
	private static int DAMAGE = 0;
	private static int COOLDOWN = 0;
	private static float SPEED = 0;
	
	/** List of strings that can be spoken */
	private List<String> dialogue;
	
	/** Line currently being spoken */
	private int speakLine;
	/** Time remaining for line to be displayed */
	private int speakTime = 0;
	  
	/** Creates a new NPC object
	 * @param pos The NPC's x, y position in pixels
	 * @param sprite The image representing the NPC
	 * @param world The world in which the NPC lives
	 * @param name The NPC's name
	 * @param dialogueLoc The location of the NPC's dialogue file
	 */
	public NPC(Vector2f pos, Image sprite, World world, String name, String dialogueLoc) {
		super(pos, sprite, world, name, SPEED, HEALTH, DAMAGE, COOLDOWN);
		dialogue = FileReader.readFile(dialogueLoc);		
	}
	
	/* Getters and setters */
	public int getSpeakTime() {
		return speakTime;
	}

	/** Displays the chosen line from an NPC's dialogue selection
	 * @param line The line number to speak from the file
	 */
	public void speak(int line) {
		speakLine = line;
		speakTime = SPEAK_TIME;
	}
	
	/* (non-Javadoc)
	 * NPC Doesn't actually take damage, just complains about it
	 * @see game.object.Character#takeDamage(int, game.object.Character)
	 */
	@Override
	protected boolean takeDamage(int damage, Character attacker) {
		speak(0);
		return false;
	}
	
	/** Updates speak timer
	 * @param delta Milliseconds that have passed since last update
	 */
	public void update(int delta) {
		if (speakTime >= 0) {
			speakTime -= delta;
		}
	}
	
	/* (non-Javadoc)
	 * Renders dialogue to be spoken, if necessary
	 * @see game.object.Character#render(org.newdawn.slick.Graphics, game.framework.Camera)
	 */
	@Override
	public void render(Graphics g, Camera camera) {
		super.render(g, camera);
		if (speakTime > 0) {
			TextRenderer.renderText(g, getCenterX()-camera.getMinX(), getMinY()-camera.getMinY(), 0, dialogue.get(speakLine));
		}
	}

}
