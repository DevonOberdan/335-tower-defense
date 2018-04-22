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
public class Skeleton extends Enemy{
	
	// data structures built here to be passed into super constructor
	
	private final static int speed = 3;
	private final static int health = 100;
	private final static int damage = 6;
	private final static int reward = 15;
	
	private static Point walkDims = new Point(75,78);
	private static Point deathDims = new Point(80,122);
	
	private static String[] skeleton = new String[]{"file:images/enemies/skeleton/skeleton_right.png",
											     "file:images/enemies/skeleton/skeleton_left.png"};
	
	private static String[] dead_skeleton = new String[] {"file:images/enemies/skeleton/dead_skeleton_right.png", 
											   		   "file:images/enemies/skeleton/dead_skeleton_left.png"};
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public Skeleton(Path path, Point start) {
		//speed, health, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(speed, health, damage, reward, walkDims, deathDims, 6, 6, skeleton, dead_skeleton, path, start);
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