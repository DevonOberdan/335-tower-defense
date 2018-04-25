package model.enemy;

import java.awt.Point;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Path;

/**
 * Enemy subclass that represents the Skeleton Enemy type, with its
 * specific attributes.
 * 
 * @author Devon Oberdan
 *
 */
public class Ghost extends Enemy{
	
	// data structures built here to be passed into super constructor
	
	private final static int speed = 5;
	private final static int health = 75;
	private final static int damage = 20;
	private final static int reward = 45;
	
	private boolean beenSpooky = false;
	private boolean isSpooky = false;
	private int iter;
	
	private static Point walkDims = new Point(47,73);
	private static Point deathDims = new Point(56,77);
	
	private static String[] ghost = new String[]{"file:images/enemies/ghost/ghost_right.png",
											     "file:images/enemies/ghost/ghost_left.png"};
	
	private static String[] dead_ghost = new String[] {"file:images/enemies/ghost/dead_ghost_right.png", 
											   		   "file:images/enemies/ghost/dead_ghost_left.png"};
	
	private final String[] poofImgs = {"file:images/explosion_01.png", "file:images/explosion_02.png"};
	private Image[] poofs;
	private long animStart = 0;
	private AnimationTimer spook;
	
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public Ghost(Path path, Point start) {
		//speed, health, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(speed, health, damage, reward, walkDims, deathDims, 9, 8, ghost, dead_ghost, path, start);
		this.beenSpooky = false;
		this.isSpooky = false;
		this.poofs = new Image[] {new Image(poofImgs[0]),new Image(poofImgs[1])};
		this.iter=0;
		spook = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if(animStart==0)  animStart=now;
				
				if((now - animStart >= 5.0e9)) {
					System.out.println("dffdfd");
					isSpooky=false;
					beenSpooky = true;
				}
			}
		};
	}	
	
	@Override
	public boolean canBeHit() {
		return !dead && !this.isSpooky;
	}
		
	@Override
	public void show(GraphicsContext gc) {
		if(this.getHel() < this.maxHealth && !this.isSpooky && !this.beenSpooky) {
			this.isSpooky=true;
			spook.start();
		}
		if(this.beenSpooky) spook.stop();
		
		if(this.isSpooky) {
			if(iter==0) {
				gc.drawImage(poofs[0],loc.getX()-(imgWidth/2), loc.getY()-(imgHeight/2),imgWidth,imgHeight); 
				iter++;
			}
			else if(iter==1) {
				gc.drawImage(poofs[1], loc.getX()-(imgWidth/2), loc.getY()-(imgHeight/2),imgWidth,imgHeight); 
				iter++;
			}
			gc.setGlobalAlpha(0.2);
		}
		int dir = 0;
		
		// if statements to determine which direction enemy should face
		if((path.getD() || path.getU()) && !path.getL() && !path.getR()) {
			dir = previousDir;
		}
		if(path.getR()) {
			dir = 0; previousDir = 0;
		}
		if(path.getL()) {
			dir = 1; previousDir = 1;
		}
		
		if(!dead){ // draw the walking frame
			img = walkImgs[dir];
			gc.drawImage(img, walkTick*(sourceWalkSizes.getX()), 0, sourceWalkSizes.getX(), sourceWalkSizes.getY(),
					     loc.getX()-(imgWidth/2), loc.getY()-(imgHeight/2), imgWidth, imgHeight);
			advanceWalk();
		}
		else{ // draw the death frame
			img = deathImgs[dir];
			
			gc.setGlobalAlpha(1-lagTick*0.1);

			gc.drawImage(img, deathTick*(sourceDeathSizes.getX()), 0, sourceDeathSizes.getX(), sourceDeathSizes.getY(),
					     loc.getX()-(imgWidth/2), loc.getY()-(imgHeight/2), imgWidth, imgHeight);
			gc.setGlobalAlpha(1);
			advanceDeath();
			return;
		}
		
		// determine if alive/how enemy should look
		checkStatus();
		
		gc.setGlobalAlpha(1.0);
		drawHealthBar(gc);
		this.turns = this.path.checkTurns(this.loc,this);
		move();
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