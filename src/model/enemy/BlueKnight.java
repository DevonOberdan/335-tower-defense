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
public class BlueKnight extends Enemy{
	
	// data structures built here to be passed into super constructor
	
	private final static int speed = 4;
	private final static int health = 120;
	private final static int damage = 10;
	private final static int reward = 35;
	
	private boolean selected;
	private static Point walkDims = new Point(98,120);
	private static Point deathDims = new Point(144,120);
	
	private static String[] knight = new String[]{"file:images/enemies/blueKnight/blue_knight_right.png",
											     "file:images/enemies/blueKnight/blue_knight_left.png"};
	
	private static String[] dead_knight = new String[] {"file:images/enemies/blueKnight/dead_blue_knight_right.png", 
											   		   "file:images/enemies/blueKnight/dead_blue_knight_left.png"};
	private String name;
	@Override
	public String getName() {
		return name;
	}
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public BlueKnight(Path path, Point start) {
		//speed, health, damage, reward, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(speed, health, damage, reward, walkDims, deathDims, 10, 9, knight, dead_knight, path, start);
		this.selected = false;
		this.name = "Blue Knight";
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
	
		if(this.selected) {
			gc.setGlobalAlpha(0.15);
			gc.setFill(Color.GHOSTWHITE);
			gc.fillOval(this.getLoc().getX()-imgHeight, this.getLoc().getY()-imgHeight, imgHeight*2, imgHeight*2);
			gc.setGlobalAlpha(1.0);
		}
	}
	
	@Override
	public void setSelected(boolean bool) {
		this.selected = bool;
	}
}