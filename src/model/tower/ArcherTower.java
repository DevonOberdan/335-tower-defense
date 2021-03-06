package model.tower;

import java.awt.Point;
import java.io.File;
import java.io.Serializable;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import model.enemy.Enemy;

/**
 * ArcherTower is another Tower type. 
 *
 */
public class ArcherTower extends Tower implements Serializable{

	
	private static final long serialVersionUID = -8864445600794993658L;
	private static boolean first = true;
	//private long start;
	private long previous;
	private long FIRERATE;
	private String proj = "file:images/laser.png";
	private transient AnimationTimer timer;
	private boolean animating;
	
	
	
	@Override
	public void reset() {
		super.setTowerType(ETower.archer);
		super.setImage(new Image("file:images/archer1.png"));
		super.setSoundEffect(new Media(new File("sounds/Capture.mp3").toURI().toString()));
		timer = new AnimationTimer(){
			
			@Override
			public void handle (long now) {
				if(first) {
					previous = now;
					first = false;
					//attackEnemy();
					//return;
				}
				if((now - previous >= FIRERATE) && canAttack()) {
					previous = now;
					attack();
				}
			}
		};
		timer.start();
	}
	
	/**
	 * Creates a new ArcherTower, using sound effects and 
	 * giving this tower a projectile.
	 * 
	 * Has 4 different upgrades.
	 * Damage: 50
	 * Health: it needs health.
	 * CurrentLevel: The level that this tower has been upgraded to.
	 */
	public ArcherTower(Point location) {
		//Type, damage, radius, image, cost, sound, location
		super("Archer", 15, 100, new Image("file:images/archer1.png"), 150, new Media(new File("sounds/Capture.mp3").toURI().toString()), location, "laser.mp3");
		super.setTowerType(ETower.archer);
		this.FIRERATE = (long) 0.8e9;
		
		timer = new AnimationTimer(){
			
			@Override
			public void handle (long now) {
				if(first) {
					previous = now;
					first = false;
					//attackEnemy();
					//return;
				}
				if((now - previous >= FIRERATE) && canAttack()) {
					previous = now;
					attack();
				}
			}
		};
		timer.start();
	}
	@Override
	public Enemy getPrioEnemy(List<Enemy> enemyList)
	{
		Enemy priority = null;
		if(enemyList.isEmpty()) {
			return null;
		}
		int closest = (int) Math.sqrt(Math.pow(enemyList.get(enemyList.size()-1).getLoc().getX() - this.getLocation().getX(), 2)
				            + Math.pow((enemyList.get(enemyList.size()-1).getLoc().getY() - this.getLocation().getY()), 2));
		if(closest < this.getRange() && enemyList.get(enemyList.size()-1).canBeHit())
			priority = enemyList.get(enemyList.size()-1);
		for (Enemy en : enemyList) {
			Point enLoc = en.getLoc();
			int dist = (int) Math.sqrt(Math.pow(enLoc.getX() - this.getLocation().getX(), 2) + Math.pow((enLoc.getY() - this.getLocation().getY()), 2));
			if (dist < this.getRange() && dist < closest && en.canBeHit()) {
				priority = en;
				closest = dist;
			}
		}

		return priority;
	}
	
	public boolean canAttack() {
		return (this.getCurrentEnemy() != null);
	}
	
	@Override
	public boolean attack() {
		
		if (this.getCurrentEnemy() == null)
			return false;
		this.playEffect();
		this.getCurrentEnemy().setAttacked(true);
		shoot();
		this.getCurrentEnemy().setHel(this.getCurrentEnemy().getHel()-this.getDamage());
		this.setEnemy(null);
		return true;
	}
	
	@Override
	public void shoot() {
		this.getGC().setStroke(Color.RED);
		this.getGC().strokeLine(getCurrentEnemy().getLoc().getX(), getCurrentEnemy().getLoc().getY(), 
				getLocation().getX(), getLocation().getY()-20);
		
//		Image projectile = new Image(proj);
//		
//        double xDiff = getCurrentEnemy().getLoc().getX() - getLocation().getX();
//        double yDiff = getCurrentEnemy().getLoc().getY() - getLocation().getY()-20;
//        this.getGC().save();
//        this.getGC().rotate(Math.toDegrees(Math.atan2(yDiff, xDiff)));
//        this.getGC().drawImage(projectile, getLocation().getX(),getLocation().getY()-20);
//        this.getGC().restore();
	}

	@Override
	public boolean levelUp() {
		if(this.getLevel() >= 3) 
			return false;
		this.setDamage((int)(this.getDamage() * 1.5));
		this.setRange((int)(this.getRange() * 1.5));
		this.increaseLevel();
		switch(this.getLevel()) {
		case 2:
			this.FIRERATE = (long) 0.7e9;
			this.setImage(new Image("file:images/archer2.png"));
			return true;
		case 3:
			this.FIRERATE = (long) 0.5e9;
			this.setImage(new Image("file:images/archer3.png"));
			return true;
		default:
			return false;
		}
	}

	@Override
	public List<Enemy> getPrioEnemies(List<Enemy> enemyList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getUpgradeCost() {
		return 275 * (this.getLevel());
	}
	public void show(GraphicsContext gc)
	{
		//actual tower image
		{
			//actual tower image
			this.getGC().drawImage(this.getCurrentImage(), this.getLocation().getX()-30, this.getLocation().getY()-80, 60, 80);
			if(this.getSelected()) {
				
				/*
				 * Drawing the radius of the tower's abilities on the map.
				 */
				this.getGC().setGlobalAlpha(0.15);
				this.getGC().setFill(Color.GHOSTWHITE);
				this.getGC().fillOval(this.getLocation().getX()-this.getRange(), this.getLocation().getY()-this.getRange(), this.getRange()*2, this.getRange()*2);
				this.getGC().setGlobalAlpha(1.0);
			}			 
		}
	}
	@Override
	public void startTimers() { if(timer != null) timer.start(); animating = true;}
	@Override
	public void endTimers() { if(timer != null) timer.stop(); animating = false;}
	@Override
	public boolean isAnimating() {
		return animating;
	}
	@Override
	public AnimationTimer getTimer() {
		return this.timer;
	}
}
