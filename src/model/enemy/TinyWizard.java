package model.enemy;

import java.awt.Point;
import java.util.ArrayList;

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
public class TinyWizard extends Enemy{
	
	// data structures built here to be passed into super constructor
	
	private final static int speed = 5;
	private final static int health = 120;
	private final static int damage = 5;
	private final static int reward = 20;
	
	private final static int range = 80;
	
	private ArrayList<Enemy> neighborList;
	
	private static Point walkDims = new Point(65,90);
	private static Point deathDims = new Point(75,75);
	
	private static String[] tinyWizard = new String[]{"file:images/enemies/tinyWizard/tiny_wizard_right.png",
											     "file:images/enemies/tinyWizard/tiny_wizard_left.png"};
	
	private static String[] deadTinyWizard = new String[] {"file:images/enemies/tinyWizard/dead_tiny_wizard_right.png", 
											   		   "file:images/enemies/tinyWizard/dead_tiny_wizard_left.png"};
	
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public TinyWizard(Path path, Point start) {
		//speed, health, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(speed, health, damage, reward, walkDims, deathDims, 4, 4, tinyWizard, deadTinyWizard, path, start);
		neighborList = new ArrayList<Enemy>();
	}	
	
	
	public void getPrioEnemies() {
		neighborList.clear();
		
		for (Enemy en : enList) {
			Point enLoc = en.getLoc();
			int dist = (int) Math.sqrt(Math.pow(enLoc.getX() - this.getLoc().getX(), 2) + Math.pow((enLoc.getY() - this.getLoc().getY()), 2));
			if (dist < range && en.canBeHit() && !en.equals(this)) {
				neighborList.add(en);
			}
		}
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