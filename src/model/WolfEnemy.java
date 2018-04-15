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
	private final Image wolf, crazy_wolf, angry_wolf;
	private boolean attacked;
	private boolean enraged;
	private boolean stalled;
		
	private int tick;
	
	private final Image testing = new Image("file:images/testing.png") ;

	public WolfEnemy(Path path, Point start) {
		super(2, 160, path, start);
		
		//this.path = super.getPath();
		wolf = new Image("file:images/enemies/wolf/wolf.png");
		crazy_wolf = new Image("file:images/enemies/wolf/crazy_wolf.png");
		angry_wolf = new Image("file:images/enemies/wolf/angry_wolf.png");
		
		img = wolf;
		
		this.timeline = new Timeline();
		
		this.attacked = false;
		this.tick = 0;
		this.enraged = false;
		this.healthPerc = 1.0;
	}
 
	public void show(GraphicsContext gc, int frame) {
		if (enraged) {
			super.setImage(angry_wolf);
			
		}
		
		switch (tick) {
		
		case 0:
			gc.drawImage(img, 0, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
			break;
		case 1:
			gc.drawImage(img, 60, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
			break;
		case 2:
			gc.drawImage(img, 120, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
			break;
		default:
			gc.drawImage(img, 180, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
			break;
		}
		gc.drawImage(testing, loc.getX(), loc.getY());
		this.turns = this.path.checkTurns(this.loc);
		checkStatus();
		move();
		advanceTick();
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
	public void advanceTick() {
		tick++;
		if(tick == 4) //4 is the length of the sprite sheet
			tick = 0;
	}

	
	public void checkStatus() {
		System.out.println(health);
		System.out.println(maxHealth);

		healthPerc = ((double)health / (double)maxHealth );
		System.out.println(healthPerc);
		if(healthPerc <= 0.25 && !stalled && !enraged) {
		     this.speed=0;
		     this.stalled = true;
		}
		else if(healthPerc >= 0.5 && stalled && !enraged) {
			this.speed = 4;
			stalled = false;
			enraged = true;
		}
		else if(stalled && !enraged) {
			health++;
		}
	}
	
}
