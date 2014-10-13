package framework;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TextRenderer {
	
    /** Color for plain text labels */
    private static final Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
    /** Color for text within bars */
    private static final Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
    /** Color of bar background */
    private static final Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
    /** Color of bar foreground */
    private static final Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
    
    /** Integer offset between start of bar and start of text */
    private static final int TEXT_OFFSET = 5;
    /** Minimum width of a text bar where specific width is not given */
    private static final int MIN_BAR_WIDTH = 70;
    
	/** Render a bar with a percentage foreground, and the remainder only background
	 * @param g The current Graphics object
	 * @param x Leftmost x position, in pixels
	 * @param y Topmost y position, in pixels
	 * @param width Width, in pixels
	 * @param height Height, in pixels
	 * @param percent Percentage to be filled with foreground
	 */
	public static void renderBar(Graphics g, float x, float y, int width, int height, float percent) {
		int percentage_bar_width = (int) (width * percent);
		g.setColor(BAR_BG);
	    g.fillRect(x, y, width, height);
	    g.setColor(BAR);
	    g.fillRect(x, y, percentage_bar_width, height);
	}

    /** Render text in front of a bar with some percentage foreground
	 * @param g The current Graphics object
	 * @param x Leftmost x position, in pixels
	 * @param y Topmost y position, in pixels
	 * @param width Width, in pixels
	 * @param percent Percentage to be filled with foreground
     * @param text The text to be displayed
     * @param offset The offset between bar and text
     */
    public static void renderText(Graphics g, float x, float y, int width, float percent, String text, int offset) {
    	float text_x = x + (width - g.getFont().getWidth(text)) / 2;
    	float text_y = y + offset;
    	int height = g.getFont().getLineHeight()+2*offset;
    	renderBar(g, x, y, width, height, percent);
    	g.setColor(VALUE);
    	g.drawString(text, text_x, text_y);
	}

    /** Render text in front of a bar with some percentage foreground and default offset
	 * @param g The current Graphics object
	 * @param x Leftmost x position, in pixels
	 * @param y Topmost y position, in pixels
	 * @param width Width, in pixels
	 * @param percent Percentage to be filled with foreground
     * @param text The text to be displayed
     */
    public static void renderText(Graphics g, float x, float y, int width, float percent, String text) {
    	renderText(g, x, y, width, percent, text, TEXT_OFFSET);
    }

    /** Render text in front of a bar with some percentage foreground with default width and offset
	 * @param g The current Graphics object
	 * @param x Centre x position, in pixels
	 * @param y Bottommost y position, in pixels
	 * @param percent Percentage to be filled with foreground
     * @param text The text to be displayed
     */
    public static void renderText(Graphics g, float x, float y, float percent, String text) {
    	int width = Math.max(g.getFont().getWidth(text)+2*TEXT_OFFSET, MIN_BAR_WIDTH);
    	renderText(g, x-width/2, y - g.getFont().getLineHeight()*3/2, width, percent, text, 0);
    }
	
	/** Render a plain text label
	 * @param g The current Graphics object
	 * @param x Leftmost x position, in pixels
	 * @param y Topmost y position, in pixels
     * @param text The text to be displayed
	 */
	public static void renderLabel(Graphics g, float x, float y, String text) {
        g.setColor(LABEL);
        g.drawString(text, x, y);
	}
	
	/** Render a list of lines centred on some position
	 * @param g The current Graphics object
	 * @param x Centre x position, in pixels
	 * @param y Centre y position, in pixels
     * @param text The text to be displayed
	 */
	public static void renderLines(Graphics g, float x, float y, List<String> text) {
		y -= (text.size()/2)*g.getFont().getLineHeight();
		for (String line:text) {
			renderText(g, x, y, 0, line);
			y += g.getFont().getLineHeight()*2;
		}
	}
}
