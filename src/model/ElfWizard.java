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
public class ElfWizard extends Enemy{
	
	// data structures built here to be passed into super constructor
	
	private final static int speed = 5;
	private final static int health = 100;
	private final static int damage = 15;
	private final static int reward = 25;
	
	private static Point walkDims = new Point(416,473);
	private static Point deathDims = new Point(350,330);
	
	private static String[] elfWizard = new String[]{"file:images/enemies/elfWizard/elf_wizard_right.png",
											     "file:images/enemies/elfWizard/elf_wizard_left.png"};
	
	private static String[] deadElfWizard = new String[] {"file:images/enemies/elfWizard/dead_elf_wizard_right.png", 
											   		   "file:images/enemies/elfWizard/dead_elf_wizard_left.png"};
	
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public ElfWizard(Path path, Point start) {
		//speed, health, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(speed, health, damage, reward, walkDims, deathDims, 5, 5, elfWizard, deadElfWizard, path, start);
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