package game.framework;

import game.object.Item;
import game.object.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class StatusBar {
	
	private static final String IMAGE_LOC = "assets/panel.png";
	
	private Image panel;
	private Player player;
	
	public StatusBar(Player player) throws SlickException {
		panel = new Image(IMAGE_LOC);
		this.player = player;
	}
	
	/** Renders the player's status panel.
     * @param g The current Slick graphics context.
     */
    public void render(Graphics g)
    {
        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float percent;       // Player's health/cooldown, as a percentage

        // Panel background image
        panel.draw(0, RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT);

        // Display the player's health
        text_x = 15;
        text_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 25;
        TextRenderer.renderLabel(g, text_x, text_y, "Health:");
                
        text = ((Integer)player.getCurrentHealth()).toString()+"/"+((Integer)player.getHealth()).toString();                          
        bar_x = 90;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 20;
        bar_width = 90;
        percent = (float)player.getCurrentHealth()/player.getHealth();
        TextRenderer.renderText(g, bar_x, bar_y, bar_width, percent, text);

        // Display the player's damage and cooldown
        text_x = 190;
        TextRenderer.renderLabel(g, text_x, text_y, "Damage:");
        
        text = ((Integer)player.getDamage()).toString();
        bar_x = text_x+70;
        bar_width = g.getFont().getWidth(text)+30;
        percent = (float)player.getLastDamage()/player.getDamage();
        TextRenderer.renderText(g, bar_x, bar_y, bar_width, percent, text);
        
        text_x += 45;
        TextRenderer.renderLabel(g, text_x, text_y, "Rate:");
        
        text = ((Integer)player.getCooldown()).toString();
        bar_x = text_x+55;
        bar_width = g.getFont().getWidth(text)+30;
        percent = (float)player.getCurrentCooldown()/player.getCooldown();
        TextRenderer.renderText(g, bar_x, bar_y, bar_width, percent, text);

        // Display the player's inventory
        text_x += 50;
        TextRenderer.renderLabel(g, text_x, text_y, "Items:");
        bar_x = text_x + 60;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 10;
        bar_width = 288;
        bar_height = 50;
        TextRenderer.renderBar(g, bar_x, bar_y, bar_width, bar_height, 0);

        inv_x = bar_x;
        inv_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT
            + ((RPG.PANEL_HEIGHT - 72) / 2);
        for (Item o:player.getItemList())
        {
            o.render(inv_x, inv_y);
            inv_x += 72;
        }
    }
}
