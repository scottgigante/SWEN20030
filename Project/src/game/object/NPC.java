package game.object;

import game.framework.FileReader;
import game.framework.World;

import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class NPC extends Character {

	private static int HEALTH = 1;
	private static int DAMAGE = 0;
	private static int COOLDOWN = 0;
	private static float SPEED = 0;
	
	private List<String> dialogue;
	  
	public NPC(Vector2f pos, Image sprite, World world, String name, String dialogueLoc) {
		super(pos, sprite, world, name, SPEED, HEALTH, DAMAGE, COOLDOWN);
		dialogue = FileReader.readFile(dialogueLoc);		
	}
	
	public void speak(int line) {
		
	}

}
