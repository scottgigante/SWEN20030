package game.framework;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TextRenderer {
	
    private static final Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
    private static final Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
    private static final Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
    private static final Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
    
    private static final int TEXT_OFFSET = 5;
    
	public static void renderBar(Graphics g, int x, int y, int width, float percent, String text) {
        int percentage_bar_width = (int) (width * percent);
        int text_x = x + (width - g.getFont().getWidth(text)) / 2;
        int text_y = y + TEXT_OFFSET;
        int height = g.getFont().getLineHeight();
        g.setColor(BAR_BG);
        g.fillRect(x, y, width, height);
        g.setColor(BAR);
        g.fillRect(x, y, percentage_bar_width, height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
	}
}
