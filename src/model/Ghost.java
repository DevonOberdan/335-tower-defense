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
public class Ghost extends Enemy{
	
	// data structures built here to be passed into super constructor
	private static Point walkDims = new Point(47,73);
	private static Point deathDims = new Point(56,77);
	
	private static String[] ghost = new String[]{"file:images/enemies/ghost/ghost_right.png",
											     "file:images/enemies/ghost/ghost_left.png"};
	
	private static String[] dead_ghost = new String[] {"file:images/enemies/ghost/dead_ghost_right.png", 
											   		   "file:images/enemies/ghost/dead_ghost_left.png"};
	
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public Ghost(Path path, Point start) {
		//speed, health, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(8, 60, walkDims, deathDims, 9, 8, ghost, dead_ghost, path, start);
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