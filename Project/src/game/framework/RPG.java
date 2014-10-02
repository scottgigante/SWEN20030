/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Matt Giuca <mgiuca>
 * Modified: Scott Gigante <gigantes>
 */

package game.framework;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/** Main class for the Role-Playing Game engine.
 * Handles initialisation, input and rendering.
 */
public class RPG extends BasicGame
{
    private World world;
    
    /** Controls various debug features */
    public static final boolean DEBUG = false;
    
    /** Height of the display panel, in pixels */
    public static final int PANEL_HEIGHT = 70;
    /** Screen width, in pixels. */
    protected static final int SCREEN_WIDTH = 800;
    /** Screen height, in pixels. */
    protected static final int SCREEN_HEIGHT = 600;
    /** Location of font file */
    private static final String FONT_LOC = "assets/DejaVuSans-Bold.ttf";
    
    /** Font to display character names, etc */
    Font font;

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
    	// TODO load font
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
    	// TODO set font
        world.render(g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new RPG());
        app.setShowFPS(DEBUG);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }
}
