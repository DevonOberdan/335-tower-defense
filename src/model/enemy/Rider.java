package model.enemy;

import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Path;

/**
 * Enemy subclass that represents the Skeleton Enemy type, with its
 * specific attributes.
 * 
 * @author Devon Oberdan
 *
 */
public class Rider extends Enemy{
	
	// data structures built here to be passed into super constructor
	
	private final static int speed = 8;
	private final static int health = 80;
	private final static int damage = 14;
	private final static int reward = 25;
	
	private static Point imgDims = new Point(100,112);
	
	private static String[] rider = new String[]{"file:images/enemies/rider/rider_right.png",
											     "file:images/enemies/rider/rider_left.png"};
	
	private static String[] dead_rider = new String[] {"file:images/enemies/rider/dead_rider_right.png", 
											   		   "file:images/enemies/rider/dead_rider_left.png"};
	
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public Rider(Path path, Point start) {
		//speed, health, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(speed, health, damage, reward, imgDims, imgDims, 12, 6, rider, dead_rider, path, start);
	}	
	
	/**
	 * Method to draw health bar right above the Enemy.
	 * 
	 * @author Devon Oberdan
	 */
	@Override
	public void drawHealthBar(GraphicsContext gc) {
		double currentHealth = (imgWidth*0.6)*healthPerc;
		
		int xShift = 10;
		
		// draw bar to show current health
		gc.setFill(Color.LIME);
		gc.fillRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2)-5, currentHealth, 4);

		// draw border to show the full health amount
		gc.setStroke(Color.BLACK);		
		gc.strokeRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2)-5, imgWidth*0.6, 4);
	}
}