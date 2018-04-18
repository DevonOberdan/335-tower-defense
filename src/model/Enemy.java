package model;

import java.awt.Point;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * abstract enemy provides us with a skeleton to use for other types
 * of enemies and the various methods that we will need to be using
 * for each one. 
 *
 */
public abstract class Enemy{

	protected int health;
	protected double healthPerc;
	
	
	protected Point loc;
//	protected int bounty;
	protected Path path;
	public int sx, sy, sw, sh, dx, dy, dw, dh;
//	protected Timeline timeline;
	protected double xOffset = 0;
	protected double yOffset = 0;
	protected Point turns;
	protected boolean attacked;	 
	protected int speed;
	protected final Image testing = new Image("file:images/testing.png") ;
	protected Image img;
	protected int imgWidth;
	protected int imgHeight;
	protected int damage;
	
	public Enemy(int speed, int health, Path path, Point start, int damage) {
		this.loc = start;
		this.speed = speed;
		this.path = path;
		this.health = health;
		this.turns = new Point(1,1);	
		this.damage = damage;
	}
	
	public void setLoc(Point p)     { loc = p;       }
	public void setHel(int h)       { health = h;    }
	public void setImage(Image img) { this.img = img;}
	
	public int   getSpeed()     { return speed;     }
	public Path  getPath()      { return path;      }
	public Point getTurns()     { return turns;     }
	public Point getLoc()       { return loc;       }
	public int   getHel()       { return health;    }
	public Image getImage()     { return img;       }
	public int   getImgWidth()  { return imgWidth;  }
	public int   getImgHeight() { return imgHeight; }
	
	/*public void showEnemy() {
		 timeline.play();
	}*/
	
	public abstract void setAttacked(boolean v);
	
	public boolean withinRange(Tower t) {
		int dist = (int) Math.sqrt(Math.pow(loc.getX() - t.getLocation().getX(), 2) +
				Math.pow((loc.getY() - t.getLocation().getY()), 2));
		if (dist <= t.getRange())
			return true;
		
		return false;
	}
	
	public boolean attackPlayer(Player p) {
		if (loc.getX()>469) {
			p.doDamage(this.damage);
			return true;
		}
		return false;
	}
	
	public abstract void show(GraphicsContext gc);
	public abstract void advanceWalk();
	public abstract void advanceDeath();
	public abstract boolean canBeHit();
	public abstract int getDeathTicker();
	public abstract int deathFrameCount();
	
	public abstract void drawHealthBar(GraphicsContext gc);
}
