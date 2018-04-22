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
public class BlueKnight extends Enemy{
	
	// data structures built here to be passed into super constructor
	
	private final static int speed = 4;
	private final static int health = 120;
	private final static int damage = 10;
	private final static int reward = 35;
	
	private static Point walkDims = new Point(98,120);
	private static Point deathDims = new Point(144,120);
	
	private static String[] knight = new String[]{"file:images/enemies/blueKnight/blue_knight_right.png",
											     "file:images/enemies/blueKnight/blue_knight_left.png"};
	
	private static String[] dead_knight = new String[] {"file:images/enemies/blueKnight/dead_blue_knight_right.png", 
											   		   "file:images/enemies/blueKnight/dead_blue_knight_left.png"};

	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public BlueKnight(Path path, Point start) {
		//speed, health, damage, reward, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(speed, health, damage, reward, walkDims, deathDims, 10, 9, knight, dead_knight, path, start);
	}
	
	/**
	 * Method to draw health bar right above the Enemy.
	 * 
	 * @author Devon Oberdan
	 */
	@Override
	public void drawHealthBar(GraphicsContext gc) {		
		double currentHealth = (imgWidth*0.6)*healthPerc;
		int xShift = 15;
		
		// draw bar to show current health
		gc.setFill(Color.LIME);
		gc.fillRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2), currentHealth, 4);

		// draw border to show the full health amount
		gc.setStroke(Color.BLACK);		
		gc.strokeRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2), imgWidth*0.6, 4);
	}	
}