package game.framework;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TextRenderer {
	
    private static final Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
    private static final Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
    private static final Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
    private static final Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
    
    private static final int TEXT_OFFSET = 5;
    
	public static void renderBar(Graphics g, float x, float y, int width, int height, float percent) {
		int percentage_bar_width = (int) (width * percent);
		g.setColor(BAR_BG);
	    g.fillRect(x, y, width, height);
	    g.setColor(BAR);
	    g.fillRect(x, y, percentage_bar_width, height);
	}
    
    public static void renderText(Graphics g, float x, float y, int width, float percent, String text, int offset) {
    	float text_x = x + (width - g.getFont().getWidth(text)) / 2;
    	float text_y = y + offset;
    	int height = g.getFont().getLineHeight()+2*offset;
    	renderBar(g, x, y, width, height, percent);
    	g.setColor(VALUE);
    	g.drawString(text, text_x, text_y);
	}

    public static void renderText(Graphics g, float x, float y, int width, float percent, String text) {
    	renderText(g, x, y, width, percent, text, TEXT_OFFSET);
    }
    
    public static void renderText(Graphics g, float x, float y, float percent, String text) {
    	int width = Math.max(g.getFont().getWidth(text)+6, 70);
    	renderText(g, x-width/2, y - g.getFont().getLineHeight()*3/2, width, percent, text, 0);
    }
	
	public static void renderLabel(Graphics g, int x, int y, String text) {
        g.setColor(LABEL);
        g.drawString(text, x, y);
	}
}
