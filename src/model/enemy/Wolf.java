package model.enemy;

import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Path;

/**
 * Enemy subclass that represents the Wolf Enemy type, with its
 * specific attributes.
 * 
 * @author Devon Oberdan
 *
 */
public class Wolf extends Enemy {
	
	// data structures built here to be passed into super constructor
	
	private final static int startSpeed = 3;
	private final static int wolfHealth = 160;
	private final static int damage = 12;
	private final static int reward = 50;
	
	private final Image[] crazy_wolf, angry_wolf;
	private boolean enraged;
	private boolean stalled;
	
	private final int stallTime = 20;
	
	private int stallTick;
		
	private static Point walkDims = new Point(174,174);
	private static Point deadDims = new Point(200,157);

	
	private static String[] wolf = new String[]{"file:images/enemies/wolf/wolf_right.png",
											   "file:images/enemies/wolf/wolf_left.png"};
	
	private static String[] dead_wolf = new String[] {"file:images/enemies/wolf/dead_wolf_right.png", 
													 "file:images/enemies/wolf/dead_wolf_left.png"};
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * 
	 * Also fills in data structures and fields that are uniquely used by this Enemy type.
	 * @param path
	 * @param start
	 */
	public Wolf(Path path, Point start) {
		//speed, health, damage, reward, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(startSpeed, wolfHealth, damage, reward, walkDims, deadDims, 4, 8, wolf, dead_wolf, path, start);		
		
		crazy_wolf    = new Image[2];
		crazy_wolf[0] = new Image("file:images/enemies/wolf/crazy_wolf_right.png");
		crazy_wolf[1] = new Image("file:images/enemies/wolf/crazy_wolf_left.png");
		
		angry_wolf    = new Image[2];
		angry_wolf[0] = new Image("file:images/enemies/wolf/angry_wolf_right.png");
		angry_wolf[1] = new Image("file:images/enemies/wolf/angry_wolf_left.png");
		
		this.stallTick = 0;
		
		this.enraged  = false;
	}
 
	/**
	 * Override of show() method, contains more conditions since the Wolf
	 * has four possible states to be in.
	 * @param gc
	 */
	@Override
	public void show(GraphicsContext gc) {
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
		
		if (enraged && !dead) {      // draws Rage wolf
			img = angry_wolf[dir];
			gc.drawImage(img, walkTick*sourceWalkSizes.getX(), 0, sourceWalkSizes.getX(), sourceWalkSizes.getY(),
					     loc.getX()-(imgWidth/2), loc.getY()-(imgHeight/2), imgWidth, imgHeight);
			advanceWalk();
		}
		else if (stalled && !dead) { // draws the crazy stalled wolf
			img = crazy_wolf[dir];
			gc.drawImage(img, loc.getX()-(imgWidth/2), loc.getY()-(imgHeight/2), imgWidth, imgHeight);
		}
		else if(!dead){    // if code hits this, then normal wolf is drawn
			img = walkImgs[dir];
			gc.drawImage(img, walkTick*sourceWalkSizes.getX(), 0, sourceWalkSizes.getX(), sourceWalkSizes.getY(),
					     loc.getX()-(imgWidth/2), loc.getY()-(imgHeight/2), imgWidth, imgHeight);
			advanceWalk();
		}
		else if(dead || this.getAttackedPlayer()) {   // draw death frames
			img = deathImgs[dir];

			gc.drawImage(img, deathTick*sourceDeathSizes.getX(), 0, sourceDeathSizes.getX(),
						 sourceDeathSizes.getY(), loc.getX()-30, loc.getY()-30, imgWidth, imgHeight);
			
			advanceDeath();
			return;      // prevents health bar from being drawn and from being drawn
		}
		
		checkStatus();
		
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
		
		int xShift = 20;
		
		// draw bar to show current health
		gc.setFill(Color.LIME);
		gc.fillRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2), currentHealth, 4);

		// draw border to show the full health amount
		gc.setStroke(Color.BLACK);		
		gc.strokeRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2), imgWidth*0.6, 4);
	}
	
	/**
	 * Override of canBeHit(); also checks for the stalled variable,
	 * which is on when it transitions from normal to Rage.
	 * 
	 * @author Devon Oberdan
	 */
	@Override
	public boolean canBeHit() { return !stalled && !dead; }
	
	/**
	 * Override of checkStatus() method, this checks for
	 * extra features of the Wolf freezing when triggered,
	 * and checks if in Rage mode.
	 * 
	 * @author Devon Oberdan
	 */
	@Override
	public void checkStatus() {

		healthPerc = ((double)health / (double)maxHealth );

		// stops and becomes crazy
		if(healthPerc <= 0.25 && !stalled && !enraged && !dead) {
		     this.speed=0;
		     this.stalled = true;
		     stallTick = 0;
		}
		// in the middle of being stopped
		else if(stalled) {
			stallTick++;
			if(stallTick >= stallTime) {
				this.speed = 6;
				stalled = false;
				enraged = true;
				stallTick=0;
				health = (int) (maxHealth*0.75);
			}
		}
		// is Dead
		else if(health<1){
			enraged = false;
			dead=true;
			speed = 0;
		}
	}
	
}
