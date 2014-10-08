/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Matt Giuca <mgiuca>
 * Modified: Scott Gigante <gigantes>
 */

package game.framework;
import java.awt.Font;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.TrueTypeFont;

/** Main class for the Role-Playing Game engine.
 * Handles initialisation, input and rendering.
 */
@SuppressWarnings("deprecation")
public class RPG extends BasicGame
{
    
    /** Controls various debug features */
    public static boolean IS_DEBUG = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
    
    /** Height of the display panel, in pixels */
    protected static final int PANEL_HEIGHT = 70;
    /** Screen width, in pixels. */
    protected static final int SCREEN_WIDTH = 800;
    /** Screen height, in pixels. */
    protected static final int SCREEN_HEIGHT = 600;
    /** Location of font file */
    private static final String FONT_LOC = "assets/DejaVuSans-Bold.ttf";
    /** Size of the font */
    private static final int FONT_SIZE = 15;

    /** World controlling the game */
    private World world;
    /** Font to display character names, etc */
	private TrueTypeFont ttf;

    /** Create a new RPG object. */
    public RPG()
    {
        super("RPG Game Engine");
    }

    /** Initialise the game state.
     * @param gc The Slick game container object.
     */
	@Override
    public void init(GameContainer gc)
    throws SlickException
    {
    	Font font = new Font(FONT_LOC, Font.PLAIN, FONT_SIZE);
    	ttf = new TrueTypeFont(font, true);
        world = new World(SCREEN_WIDTH, SCREEN_HEIGHT-PANEL_HEIGHT);
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();

        // Update the player's movement direction based on keyboard presses.
        Vector2f dir = new Vector2f(0,0);
        if (input.isKeyDown(Input.KEY_DOWN))
            dir.y += 1;
        if (input.isKeyDown(Input.KEY_UP))
            dir.y -= 1;
        if (input.isKeyDown(Input.KEY_LEFT))
            dir.x -= 1;
        if (input.isKeyDown(Input.KEY_RIGHT))
            dir.x += 1;

        // Let World.update decide what to do with this data.
        world.update(dir, delta, input.isMousePressed(Input.MOUSE_LEFT_BUTTON), input.getMouseX(), input.getMouseY(), input.isKeyPressed(Input.KEY_A), input.isKeyPressed(Input.KEY_T));
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
        // Let World.render handle the rendering.
    	g.setFont(ttf);
        world.render(g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new RPG());
        app.setShowFPS(IS_DEBUG);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }
}
