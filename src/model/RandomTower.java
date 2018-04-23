package model;

import java.awt.Point;
import java.io.File;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import model.enemy.Enemy;

/**
 * ArcherTower is another Tower type. 
 *
 */
public class RandomTower extends Tower {

	
	private static boolean first = true;
	//private long start;
	private long previous;
	
	
	/**
	 * Creates a new ArcherTower, using sound effects and 
	 * giving this tower a projectile.
	 * 
	 * Has 4 different upgrades.
	 * Damage: 50
	 * Health: it needs health.
	 * CurrentLevel: The level that this tower has been upgraded to.
	 */
	public RandomTower(Point location) {
		//Type, damage, radius, image, cost, sound, location
		super("Archer", 15, 100, new Image("file:images/random.png"), 50, new Media(new File("sounds/Capture.mp3").toURI().toString()), location);
		super.setTowerType(ETower.archer);
		
		AnimationTimer timer = new AnimationTimer(){
			
			@Override
			public void handle (long now) {
				if(first) {
					previous = now;
					first = false;
					//System.out.println("First call");
					//attackEnemy();
					//return;
				}
				if((now - previous >= 1.0e9) && canAttack()) {
					previous = now;
					//System.out.println("one second later");
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
		
		this.getCurrentEnemy().setAttacked(true);
		shoot();
		System.out.println("Tower: " + getLocation() + getCurrentEnemy().getLoc());
		this.getCurrentEnemy().setHel(this.getCurrentEnemy().getHel()-this.getDamage());
		this.setEnemy(null);
		return true;
	}
	
	@Override
	public void shoot() {
		this.getGC().setStroke(Color.RED);
		this.getGC().strokeLine(getCurrentEnemy().getLoc().getX(), getCurrentEnemy().getLoc().getY(), 
				getLocation().getX(), getLocation().getY());
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
			this.setImage(new Image("file:images/archer2.png"));
			return true;
		case 3:
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
	
	public void show(GraphicsContext gc)
	{
		//actual tower image
		{
			//actual tower image
			gc.drawImage(this.getCurrentImage(), 0, 0, 150, 150, this.getLocation().getX()-30, this.getLocation().getY()-40, 60, 80);
			if(this.getSelected()) {
				gc.setGlobalAlpha(0.15);
				gc.setFill(Color.GHOSTWHITE);
				gc.fillOval(this.getLocation().getX()-this.getRange(), this.getLocation().getY()-this.getRange(), this.getRange()*2, this.getRange()*2);
				gc.setGlobalAlpha(1.0);
			}
			
			//gc.drawImage(testing, this.getLocation().getX(), this.getLocation().getY());
			 
		}
	}
}
