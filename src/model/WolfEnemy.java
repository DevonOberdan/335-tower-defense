package model;

import java.awt.Point;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class WolfEnemy extends Enemy {
	private final static int damage = 10;
	private final static int maxHealth = 160;
	private final Image[] wolf, crazy_wolf, angry_wolf, dead_wolf;
	private boolean attacked;
	private boolean enraged;
	private boolean stalled;
	private boolean dead;
	
	private final int stallTime = 20;
	
	private int walkTick;
	private int stallTick;
	private int deadTick;	
	
	private int previousDir;
	
	private final Image testing = new Image("file:images/testing.png") ;

	public WolfEnemy(Path path, Point start) {
		super(2, 160, path, start, damage, 30);
		
		wolf          = new Image[2];
		wolf[0]       = new Image("file:images/enemies/wolf/wolf_right.png");
		wolf[1]       = new Image("file:images/enemies/wolf/wolf_left.png");
		
		crazy_wolf    = new Image[2];
		crazy_wolf[0] = new Image("file:images/enemies/wolf/crazy_wolf_right.png");
		crazy_wolf[1] = new Image("file:images/enemies/wolf/crazy_wolf_left.png");
		
		angry_wolf    = new Image[2];
		angry_wolf[0] = new Image("file:images/enemies/wolf/angry_wolf_right.png");
		angry_wolf[1] = new Image("file:images/enemies/wolf/angry_wolf_left.png");
		
		dead_wolf     = new Image[2];
		dead_wolf[0]  = new Image("file:images/enemies/wolf/dead_wolf_right.png");
		dead_wolf[1]  = new Image("file:images/enemies/wolf/dead_wolf_left.png");

		this.setImage(wolf[0]);
		this.setImageWidth(60);
		this.setImageHeight(60);		
		
		this.walkTick  = 0;
		this.stallTick = 0;
		this.deadTick  = 0;
		
		this.attacked = false;
		this.enraged  = false;
		this.dead     = false;
		this.healthPerc = 1.0;
		
		this.previousDir = 0;
	}
 
	public void show(GraphicsContext gc) {
		int dir = 0;
		
		if((path.getD() || path.getU()) && !path.getL() && !path.getR()) {
			dir = previousDir;
		}
		
		if(path.getR()) {
			dir = 0; previousDir = 0;
		}
		if(path.getL()) {
			dir = 1; previousDir = 1;
		}
		
		if (!dead && enraged) {
			this.setImage(angry_wolf[dir]); 
			gc.drawImage(this.getImage(), walkTick*174, 0, 174, 174, loc.getX()-(this.getImgWidth()/2), loc.getY()-(this.getImgHeight()/2), this.getImgWidth(), this.getImgHeight());
			advanceWalk();
		}
		else if (!dead && stalled) {
			this.setImage(crazy_wolf[dir]); 
			gc.drawImage(this.getImage(), loc.getX()-(this.getImgWidth()/2), loc.getY()-(this.getImgHeight()/2), this.getImgWidth(), this.getImgHeight());
		}
		else if(!dead){
			this.setImage(wolf[dir]); 
			gc.drawImage(this.getImage(), walkTick*174, 0, 174, 174, loc.getX()-(this.getImgWidth()/2), loc.getY()-(this.getImgHeight()/2), this.getImgWidth(), this.getImgWidth());
			advanceWalk();
		}
		else if(dead) {
			this.setImage(dead_wolf[dir]); 

			gc.drawImage(this.getImage(), deadTick*200, 0, 200, 157, loc.getX()-30, loc.getY()-30, 60, 60);
			
			advanceDeath();
			return;
		}
		
		checkStatus();
		
		gc.drawImage(testing, loc.getX(), loc.getY());
		drawHealthBar(gc);
		this.checkTurns();
		move();
	}
	
	
	@Override
	public void drawHealthBar(GraphicsContext gc) {
		double currentHealth = (this.getImgWidth()*0.6)*healthPerc;
		
		gc.setFill(Color.LIME);
		gc.fillRect(loc.getX()-(this.getImgWidth()/2)+20, loc.getY()-(this.getImgHeight()/2), currentHealth, 4);

		gc.setStroke(Color.BLACK);		
		gc.strokeRect(loc.getX()-(this.getImgWidth()/2)+20, loc.getY()-(this.getImgHeight()/2), this.getImgWidth()*0.6, 4);
	}
	
	@Override
	public void setAttacked(boolean v) {
		attacked = v;
	}
	
	public void move() {
		this.loc = (new Point((int)(loc.getX() + this.getSpeed()*this.getTurns().getX()),
				(int)(loc.getY() + this.getSpeed()*this.getTurns().getY())));
	}
	public void checkTurns() {
		this.setTurns(this.path.checkTurns(this.loc, this));
	}

	@Override
	public void advanceWalk() {
		walkTick++;
		if(walkTick == 4) //4 is the length of the sprite sheet
			walkTick = 0;
	}
	@Override
	public void advanceDeath() {
		deadTick++;
	}
	@Override
	public int getDeathTicker() {
		return deadTick;
	}
	@Override
	public int deathFrameCount() {
		return 8;
	}
	@Override
	public boolean canBeHit() {
		return !stalled && !dead;
	}
	
	public void checkStatus() {

		healthPerc = ((double)health / (double)maxHealth );

		// stops and becomes crazy
		if(healthPerc <= 0.25 && !stalled && !enraged && !dead) {
			this.setSpeed(0);
			 this.stalled = true;
		     stallTick = 0;
		}
		// in the middle of being stopped
		else if(stalled) {
			stallTick++;
			if(stallTick >= stallTime) {
				this.setSpeed(6);
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
			this.setSpeed(0);
		}
	}
}
