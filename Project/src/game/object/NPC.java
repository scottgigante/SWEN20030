package game.object;

import game.framework.Camera;
import game.framework.FileReader;
import game.framework.GameObject;
import game.framework.TextRenderer;
import game.framework.World;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class NPC extends Character {
	private static final long serialVersionUID = -1382699989591165743L;
	private static final int SPEAK_TIME = 3000;
	
	/** NPC stats: health 1 to show red name bar, no other action */
	private static int HEALTH = 1;
	private static int DAMAGE = 0;
	private static int COOLDOWN = 0;
	private static float SPEED = 0;
	
	/** List of strings that can be spoken */
	private List<String> dialogue;
	
	/** Line to be spoken */
	private int speakLine;
	/** Time remaining for line to be displayed */
	private int speakTime = 0;
	  
	public NPC(Vector2f pos, Image sprite, World world, String name, String dialogueLoc) {
		super(pos, sprite, world, name, SPEED, HEALTH, DAMAGE, COOLDOWN);
		dialogue = FileReader.readFile(dialogueLoc);		
	}
	
	public int getSpeakTime() {
		return speakTime;
	}

	/** Displays the chosen line from an NPC's dialogue selection
	 * @param g The Graphics object on which to draw
	 * @param line The line number to speak from the file
	 */
	public void speak(int line) {
		speakLine = line;
		speakTime = SPEAK_TIME;
	}
	
	@Override
	public boolean takeDamage(int damage, Character attacker) {
		speak(0);
		return false;
	}
	
	public void update(int delta) {
		if (speakTime >= 0) {
			speakTime -= delta;
		}
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		super.render(g, camera);
		if (speakTime > 0) {
			TextRenderer.renderText(g, getCenterX()-camera.getMinX(), getMinY()-camera.getMinY(), 0, dialogue.get(speakLine));
		}
	}

}
