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
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int percentage_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float percent;       // Player's health/cooldown, as a percentage

        // Panel background image
        panel.draw(0, RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT);

        // Display the player's health
        text_x = 15;
        text_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
                
        text = ((Integer)player.getCurrentHealth()).toString()+"/"+((Integer)player.getHealth()).toString();                          
        bar_x = 90;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 20;
        bar_width = 90;
        bar_height = 30; // TODO remove
        percent = (float)player.getCurrentHealth()/player.getHealth(); // TODO: HP / Max-HP
        TextRenderer.renderBar(g, bar_x, bar_y, bar_width, percent, text);

        // Display the player's damage and cooldown
        text_x = 190;
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text = ((Integer)player.getDamage()).toString(); // TODO: Damagebar_x = text_x+55;
        bar_x = text_x+70;
        bar_width = g.getFont().getWidth(text)+30;
        percent = (float)player.getLastDamage()/player.getDamage(); // TODO: HP / Max-HP
        percentage_bar_width = (int) (bar_width * percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, percentage_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        
        text_x += 45;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        
        text = ((Integer)player.getCooldown()).toString();
        bar_x = text_x+55;
        bar_width = g.getFont().getWidth(text)+30;
        percent = (float)player.getCurrentCooldown()/player.getCooldown(); // TODO: HP / Max-HP
        percentage_bar_width = (int) (bar_width * percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, percentage_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        text_x += 50;
        g.setColor(LABEL);
        g.drawString("Items:", text_x, text_y);
        bar_x = text_x + 60;
        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

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
