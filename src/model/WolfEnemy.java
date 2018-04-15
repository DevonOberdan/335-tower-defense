package model;

import java.awt.Point;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class WolfEnemy extends Enemy {
	private final static int maxHealth = 160;
	private final Image wolf, crazy_wolf, angry_wolf, dead_wolf;
	private boolean attacked;
	private boolean enraged;
	private boolean stalled;
	private boolean dead;
	
	private final int stallTime = 20;
	
	private int walkTick;
	private int stallTick;
	private int deadTick;	
	
	private final Image testing = new Image("file:images/testing.png") ;

	public WolfEnemy(Path path, Point start) {
		super(2, 160, path, start);
		
		//this.path = super.getPath();
		wolf = new Image("file:images/enemies/wolf/wolf_right.png");
		crazy_wolf = new Image("file:images/enemies/wolf/crazy_wolf_right.png");
		angry_wolf = new Image("file:images/enemies/wolf/angry_wolf_right.png");
		dead_wolf  = new Image("file:images/enemies/wolf/dead_wolf_right.png");
		img = wolf;
		
		this.timeline = new Timeline();
		
		this.walkTick  = 0;
		this.stallTick = 0;
		this.deadTick  = 0;
		
		this.attacked = false;
		this.enraged  = false;
		this.dead     = false;
		this.healthPerc = 1.0;
	}
 
	public void show(GraphicsContext gc) {
		if (enraged) {
			super.setImage(angry_wolf);
			gc.drawImage(img, walkTick*60, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
			advanceWalk();
		}
		else if (stalled) {
			super.setImage(crazy_wolf);
			gc.drawImage(crazy_wolf, loc.getX()-30, loc.getY()-30, 60, 60);
		}
		else if(!dead){
			img = wolf;
			gc.drawImage(img, walkTick*60, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
			advanceWalk();
		}
		else if(dead) {
			img = dead_wolf;

			gc.drawImage(img, deadTick*200, 0, 200, 157, loc.getX()-30, loc.getY()-30, 60, 60);
			
			advanceDeath();
		}
		gc.drawImage(testing, loc.getX(), loc.getY());
		this.turns = this.path.checkTurns(this.loc);
		checkStatus();
		move();
	}
	
	@Override
	public void setAttacked(boolean v) {
		attacked = v;
	}
	
	public void move() {
		this.loc = (new Point((int)(loc.getX() + speed*turns.getX()),
				(int)(loc.getY() + speed*turns.getY())));
	}
	public void checkTurns() {
		this.turns = this.path.checkTurns(this.loc);
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
	//	System.out.println(health);
	//	System.out.println(maxHealth);

		healthPerc = ((double)health / (double)maxHealth );
	//	System.out.println(healthPerc);
		
		// stops and becomes crazy
		if(healthPerc <= 0.25 && !stalled && !enraged && !dead) {
		     this.speed=0;
		     this.stalled = true;
		     stallTick = 0;
		}
		// in the middle of being stopped
		else if(stalled) {
			stallTick++;
			//System.out.println("STALLTICK: "+ stallTick);
			if(stallTick >= stallTime) {
				this.speed = 6;
				stalled = false;
				enraged = true;
				stallTick=0;
				health *= 2;
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
