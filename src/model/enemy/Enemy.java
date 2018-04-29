package model.enemy;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Path;
import model.Player;
import model.tower.Tower;

/**
 * Abstract Enemy provides us with a skeleton to use for other types
 * of enemies and the various methods that we will need to be using
 * for each one. 
 *
 * @author Devon Oberdan
 */
public abstract class Enemy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1955527335489047490L;
	// the many fields shared by all Enemy types
	protected int speed;
	protected int health;
	protected int maxHealth;
	protected double healthPerc;
	
	protected final int imgWidth  = 60;
	protected final int imgHeight = 60;
	
	protected Path path;
	protected Point loc;
	
	protected int reward;
	protected int damage;
	
	protected Point turns;
	protected boolean attacked;	 
	private int numTurns;
	private int pathNum;
	protected boolean dead;
	protected boolean attackedPlayer;
	
	protected Image[] walkImgs  = new Image[2];
	protected Image[] deathImgs = new Image[2];
	
	protected Image img;
	
	protected Point sourceWalkSizes  = new Point(0,0);
	protected Point sourceDeathSizes = new Point(0,0);
	
	protected int deathTick;
	protected int deathFrames;
	
	protected int walkTick;
	protected int walkFrames;
	
	protected int lagTick;
	protected int lagFrames;
	
	protected int previousDir;
	
	protected boolean boosted;
	
	protected ArrayList<Enemy> enList;
	
	/**
	 * Abstract Enemy constructor used by all Enemy subclasses. Populates
	 * fields and data structures that all Enemy types share.
	 * 
	 * @param speed
	 * @param health
	 * @param walkSizes
	 * @param dieSizes
	 * @param walkFrames
	 * @param dieFrames
	 * @param walkFiles
	 * @param dieFiles
	 * @param path
	 * @param start
	 * 
	 * @author Devon Oberdan
	 */
	public Enemy(int speed, int health, int damage, int reward, Point walkSizes,
			     Point dieSizes, int walkFrames, int dieFrames, String[] walkFiles, 
				 String[] dieFiles, Path path, Point start) {
		
		this.speed       = speed; 
		this.health      = health;
		this.maxHealth   = health;
		
		this.damage = damage;
		this.reward = reward;
		
		// Point objects to stores the pixel size of the specific frames
		sourceWalkSizes.setLocation(walkSizes.getX(), walkSizes.getY());
		sourceDeathSizes.setLocation(dieSizes.getX(), dieSizes.getY());
		
		// tickers to iterate over sheets
		this.walkTick  = 0;
		this.deathTick = 0;
		this.lagTick   = 0;
		
		// total number of frames for each state
		this.walkFrames  = walkFrames;
		this.deathFrames = dieFrames;
		this.lagFrames   = 10;
		
		// store given Images into proper variables
		for(int i=0; i<2;i++) {
			this.walkImgs[i] = new Image(walkFiles[i]);
			this.deathImgs[i] = new Image(dieFiles[i]);
		}
		
		this.img = walkImgs[0];
		
		this.loc = start;
		this.path = path;
		this.turns = new Point(1,1);	
		
		this.attacked = false;
		this.dead = false;
		
		this.healthPerc = 1.0;
		
		this.previousDir = 0;
		this.numTurns = 0;
		this.pathNum = (int) (Math.random()*3);
		
		this.enList = new ArrayList<>();
		this.boosted = false;
	}
	
	/* setters */
	public void setLoc(Point p)			{ loc = p;          		}
	public void setHel(int newHealth)	{ health = newHealth; 	}
	public void setTurns(Point p)		{ this.turns = p;    	}
	public void setSpeed(int s)			{ this.speed = s;   		}
	public void updateNumTurns()			{ numTurns++;       		}
	public void setAttackPlayer()        { attackedPlayer = true;}
	public void setDead()		{ this.dead = true; }
	/* getters */
	public int getReward()		{ return this.reward;}
	public boolean getDead()	{ return this.dead; }
	public int   getSpeed()     { return speed;     }
	public Path  getPath()      { return path;      }
	public Point getTurns()     { return turns;     }
	public Point getLoc()       { return loc;       }
	public int   getHel()       { return health;    }
	public int   getImgWidth()  { return imgWidth;  }
	public int   getImgHeight() { return imgHeight; }
	public int   getNumTurns()  { return numTurns;  }
	public int 	 getPathNum()   { return pathNum;   }
	public boolean getAttackedPlayer() {return attackedPlayer;}
	public int getDeathTicker() { return deathTick; }
	public int deathFrameCount() { return deathFrames; }
	
	
	/**
	 *  Advances deathTick until we have fully iterated through the
	 *  death spritesheet, and then it iterates lagTick, which is
	 *  used to keep the dead body on screen for a few seconds.
	 *  @author Devon Oberdan
	 */
	public void advanceDeath() { 
		if(deathTick<deathFrames-1) deathTick++;	
		else lagTick++;
	}
	
	public boolean doWeRemove() { return lagTick >= lagFrames; }
	
	/**
	 *  Checks if this Enemy is contained within the range of the
	 *  given Tower.
	 *  @author Devon Oberdan
	 */
	public boolean withinRange(Tower t) {
		int dist = (int) Math.sqrt(Math.pow(loc.getX() - t.getLocation().getX(), 2) +
				   Math.pow((loc.getY() - t.getLocation().getY()), 2));
		
		return (dist <= t.getRange());
	}
	
	public boolean attackPlayer(Player p, Point endZone) {
		if (endZone.getX()<250 && endZone.getY()<250) {
			if (loc.getX()<endZone.getX() && loc.getY()<endZone.getY()) {
				p.doDamage(this.damage);
				return true;
			}
		}
		else {
		if (loc.getX()>endZone.getX() || loc.getY()>endZone.getY()) {
			p.doDamage(this.damage);
			return true;
		}
		}
		return false;
	}
	/**
	 *  Updates loc based on the Enemy's speed and current turns list.
	 *  @author Devon Oberdan
	 */
	public void move() {
		this.loc = (new Point((int)(loc.getX() + speed*turns.getX()),
				(int)(loc.getY() + speed*turns.getY())));
	}
	
	/**
	 *  Updates attacked variable.
	 *  @author Devon Oberdan
	 */
	public void setAttacked(boolean v) { attacked = v; }
	
	/**
	 *  Updates walkTick to wrap around the spritesheet.
	 *  @author Devon Oberdan
	 */
	public void advanceWalk() { walkTick = (walkTick+1)%walkFrames; }
	
	/**
	 *  Updates the turns list at current frame.
	 */
	public void checkTurns() {
		this.turns = this.path.checkTurns(this.loc,this);
	}
	
	/* Abstract methods and other methods that are possibly overwritten by certain
	 * Enemy types
	 */
	/**
	 * Standard canBeHit() method used by an Enemy subclass unless they
	 * override it to be more specific.
	 * Checks if Enemy is in death frame, and is used to prevent Towers
	 * from shooting the dead body.
	 * 
	 * @author Devon Oberdan
	 */
	public boolean canBeHit() { return !dead; }
	
	/**
	 * Standard checkStatus() method used by an Enemy subclass unless they
	 * override it to be more specific.
	 * Updates healthPerc variable, which is used when drawing the health
	 * bar, and also checks if enemy is alive or not.
	 * 
	 * @author Devon Oberdan
	 */
	public void checkStatus() {
		healthPerc = ((double)health / (double)maxHealth );
		dead = (healthPerc <= 0);
	}
	
	/**
	 * Standard show() method used by an Enemy subclass unless they
	 * override it to be more specific. Determines which image will
	 * be displayed in the current frame, and which direction it
	 * will face.
	 * 
	 * @param gc
	 * @author Devon Oberdan
	 */
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
		
		drawHealthBar(gc);
		this.turns = this.path.checkTurns(this.loc,this);
		move();
	}
	
	// abstract method to draw the enemy's health bar, each is positioned a bit different
	public abstract void drawHealthBar(GraphicsContext gc);
}
