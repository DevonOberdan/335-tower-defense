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
public class ElfArcher extends Enemy{

	// data structures built here to be passed into super constructor
	private static Point walkDims = new Point(400,500);
	private static Point deathDims = new Point(487,500);
	
	private static String[] archer = new String[]{"file:images/enemies/elfArcher/elf_archer_right.png",
											     "file:images/enemies/elfArcher/elf_archer_left.png"};
	
	private static String[] dead_archer = new String[] {"file:images/enemies/elfArcher/dead_elf_archer_right.png", 
											   		   "file:images/enemies/elfArcher/dead_elf_archer_left.png"};

	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public ElfArcher(Path path, Point start) {
		//speed, health, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(6, 80, walkDims, deathDims, 5, 5, archer, dead_archer, path, start);
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