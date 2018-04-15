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
import javafx.util.Duration;

/**
 * abstract enemy provides us with a skeleton to use for other types
 * of enemies and the various methods that we will need to be using
 * for each one. 
 *
 */
public abstract class Enemy{

	private int health;
	private Point pos;
//	private int bounty;
	private Path path;
	public int sx, sy, sw, sh, dx, dy, dw, dh;
	private Timeline timeline;
	private double xOffset = 0;
	private double yOffset = 0;
	private Point turns;
	private boolean attacked;	 
	private int speed;
	private final Image testing = new Image("file:images/testing.png") ;

	
	public Enemy(int speed, Path path) {
		pos = new Point();
		this.speed = speed;
		this.path = path;
		this.health = 100;
		this.turns = new Point(1,1);		
	}
	
	public void setLoc(Point p) { pos = p; }
	public void setHel(int h)   { health = h; }
	
	public int getSpeed() {return speed;}
	public Path getPath() {return path;}
	public Point getTurns() {return turns;}
	public Point getLoc()   { return pos; }
	public int   getHel()   { return health; }
	
	public void showEnemy() {
		
		 timeline.play();
	}
	
	public abstract void setAttacked(boolean v);
	
	public boolean withinRange(Tower t) {
		int dist = (int) Math.sqrt(Math.pow(pos.getX() - t.getLocation().getX(), 2) +
				Math.pow((pos.getY() - t.getLocation().getY()), 2));
		if (dist <= t.getRange())
			return true;
		
		return false;
	}
	
	public abstract void advanceTick();
	
}
