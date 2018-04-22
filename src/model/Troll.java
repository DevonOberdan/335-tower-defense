package model;

import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Enemy subclass that represents the Skeleton Enemy type, with its
 * specific attributes.
 * 
 * @author Devon Oberdan
 *
 */
public class Troll extends Enemy{
	
	// data structures built here to be passed into super constructor
	private static Point walkDims = new Point(300,227);
	private static Point deathDims = new Point(350,180);
	
	private static String[] troll = new String[]{"file:images/enemies/troll/troll_right.png",
											     "file:images/enemies/troll/troll_left.png"};
	
	private static String[] dead_troll = new String[] {"file:images/enemies/troll/dead_troll_right.png", 
											   		   "file:images/enemies/troll/dead_troll_left.png"};
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public Troll(Path path, Point start) {
		//speed, health, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(2, 240, walkDims, deathDims, 7, 6, troll, dead_troll, path, start);
	}	
	
	/**
	 * Method to draw health bar right above the Enemy.
	 * 
	 * @author Devon Oberdan
	 */
	@Override
	public void drawHealthBar(GraphicsContext gc) {
		double currentHealth = (imgWidth*0.6)*healthPerc;
		
		int xShift = 5;
		
		// draw bar to show current health
		gc.setFill(Color.LIME);
		gc.fillRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2), currentHealth, 4);

		// draw border to show the full health amount
		gc.setStroke(Color.BLACK);		
		gc.strokeRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2), imgWidth*0.6, 4);
	}
}