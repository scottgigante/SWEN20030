import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
 
public class SlickFlyingGame extends BasicGame 
	{ 
        int count = 0;
        Image background;
        GameObject plane;
	
		public SlickFlyingGame() {
				super("SlickFlyingGame"); 
	    } 
		
		@Override 
	    public void init(GameContainer container) 
	    	throws SlickException {
			
			 background = new Image("assets/land.jpeg");
			 Image sprite = new Image("assets/plane.png");
			 sprite = sprite.getScaledCopy(0.5f);
			 plane = new GameObject(container, sprite, new Vector2f(container.getWidth()/2-sprite.getWidth()/2,container.getHeight()/2-sprite.getHeight()/2), 0.5f);
		} 
	    
	    @Override public void update(GameContainer gc, int delta) 
	    throws SlickException {
	    	
            Input input = gc.getInput();

	        if(input.isKeyDown(Input.KEY_UP))
	        {
	        	plane.setSpeed(plane.getSpeed()+delta*plane.getAccel());	        	
	        }
	        if(input.isKeyDown(Input.KEY_DOWN))
	        {
	        	plane.setSpeed(plane.getSpeed()-delta*plane.getAccel());
	        }

	        if(input.isKeyDown(Input.KEY_LEFT))
	        {
	        	plane.setRotation(plane.getRotation()-delta*plane.getRotate_speed());	      	
	        }
	        if(input.isKeyDown(Input.KEY_RIGHT))
	        {
	        	plane.setRotation(plane.getRotation()+delta*plane.getRotate_speed());	
	        }	 
	     
	    } 
	    
	    @Override public void render(GameContainer container, Graphics g) 
	    throws SlickException { 
	    	background.draw(0,0);
	    	plane.update();
	    	
	    } 
	    
	    public static void main(String[] args) { 
	    	
	    	try { 
	    		AppGameContainer app = new AppGameContainer(new SlickFlyingGame()); 
	    		app.setDisplayMode(800, 600, false);
	            app.start();

	    		
	    	} catch (SlickException e) { 
	    		e.printStackTrace(); 
	    	} 
	    }
}