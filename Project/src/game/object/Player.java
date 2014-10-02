/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Scott Gigante <gigantes>
 */

package game.object;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import game.framework.GameObject;
import game.framework.RPG;
import game.framework.World;
import game.object.item.Elixir;

import java.util.ArrayList;

/** Player-character class.
 * Adds individual player functions to the generic character class
 */
public class Player extends Character {	
	private static final long serialVersionUID = 8734732098037662995L;
	/** Beginning x position */
	private static final float SPAWN_X_POS = 756;
	/** Beginning y position */
	private static final float SPAWN_Y_POS = 684;
	/** The location of the player image */
	private static final String IMAGE_LOC = "assets/units/player.png";
	
	/* Player's stats */
	private static final String NAME = "Player";
	private static final float MAX_SPEED = 0.25f;
	private static final float DEBUG_SPEED = 0.75f;
	private static final int HEALTH = 100;
	private static final int DAMAGE = 26;
	private static final int COOLDOWN = 600;
	
	/** Image file imported once and then stored statically */
	private static Image image;
	
	/** Player's inventory, consisting of up to four items */
	private ArrayList<Item> itemList;
	/** Whether or not the player will speak to an NPC it interacts with */
	private boolean isSpeak;
	/** Whether or not the player will attack a monster it interacts with */
	private boolean isAttack;
	/** Last random amount of damage inflicted held for display purposes */
	private int lastDamage=0;
	
	/** Fetches the pre-generated image for the class, or if it has not yet been generated, does so.
	 * @return Image for the player's sprite
	 */
	private static Image getImage() {
		try {
			if (image == null) {
				image = new Image(IMAGE_LOC);
			}
			return image;
		} catch (SlickException e) {
			System.out.println(e.getMessage());
			System.exit(1);
			return null;
		}
	}
	
	public int getLastDamage() {
		return lastDamage;
	}
	
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	
	public void addItem(Item o) {
		itemList.add(o);
		setHealth(getHealth()+o.getHealth());
		setCooldown(getCooldown()+o.getCooldown());
		setDamage(getDamage()+o.getDamage());
	}
	
	/** Checks if the player has the Elixir, and if so, takes it
	 * @return True or false
	 */
	public boolean hasElixir() {
		for (Item o:itemList) {
			if (o instanceof Elixir) {
				itemList.remove(o);
				return true;
			}
		}
		return false;
	}
	
	/** Constructor for the player class
	 * @param world The world in which the player lives
	 * @throws SlickException 
	 */
	public Player(World world) {
		super(new Vector2f(SPAWN_X_POS,SPAWN_X_POS), getImage(), world, NAME, (RPG.DEBUG ? DEBUG_SPEED : MAX_SPEED), HEALTH, DAMAGE, COOLDOWN);
		itemList = new ArrayList<Item>();
		setTerrainBlocking(!RPG.DEBUG);
	}
	
	/** Updates the player with a mouse press
	 * @param xDir x direction on keyboard
	 * @param yDir y direction on keyboard
	 * @param delta Milliseconds since last update
	 * @param mouseX x position of pointer when pressed relative to map
	 * @param mouseY y position of pointer when pressed relative to map
	 * @param aPressed Whether or not A has been pressed
	 * @param tPressed Whether or not T has been pressed
	 */
	public void update(Vector2f dir, int delta, int mouseX, int mouseY, boolean aPressed, boolean tPressed) {
		setPath(mouseX, mouseY);
		update(dir,delta, aPressed,tPressed);
	}
	
	/** Updates the player for key presses
	 * @param xDir x direction on keyboard
	 * @param yDir y direction on keyboard
	 * @param delta Milliseconds since last update
	 * @param aPressed Whether or not A has been pressed
	 * @param tPressed Whether or not T has been pressed
	 */
	public void update(Vector2f dir, int delta, boolean aPressed, boolean tPressed) {
		isSpeak = tPressed;
		isAttack = aPressed;
		update(dir,delta);
	}
	
	/* (non-Javadoc)
	 * Interacts with GameObjects depending on their subclass
	 * @see game.GameObject#interact(game.GameObject)
	 */
	public void interact(GameObject o) {
		if (isAttack && o instanceof Monster) {
			attack((Monster) o);
		} else if (isSpeak && o instanceof NPC) {
			
		} else if (o instanceof Item) {
			o.interact(this);
		}
	}
	
	/* (non-Javadoc)
	 * @see game.Character#attack(game.Character)
	 */
	@Override
	public boolean attack(Character o) {
		if (getCurrentCooldown() <= 0 || getCurrentCooldown() == getCooldown()) {
			lastDamage = (int)(Math.random()*getDamage());
			setCurrentCooldown(getCooldown());
			return o.takeDamage(lastDamage, this);
		}
		return false;
	}
	/* (non-Javadoc)
	 * Rather than destroying the player, it respawns
	 * @see game.GameObject#destroy()
	 */
	public void destroy() {
		setCenterX(SPAWN_X_POS);
		setCenterY(SPAWN_Y_POS);
		setCurrentHealth(getHealth());
		setCurrentCooldown(0);
		lastDamage = 0;
		getPath().clear();
	}
}
