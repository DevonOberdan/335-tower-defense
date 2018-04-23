package model;

import java.awt.Point;
import java.io.File;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import model.enemy.Enemy;

public class Catapult extends Tower{

	
	private static boolean first = true;
	//private long start;
	private long previous;
	private long prev;
	
	private int xDist;
	private int yDist;
	
	private double prevBallX;
	private double prevBallY;
	private int shotIter = 0;
	
	private Image ball = new Image("file:images/cannon_ball.png");
	
	private Point fireLoc;
	
	/**
	 * Creates a new ArcherTower, using sound effects and 
	 * giving this tower a projectile.
	 * 
	 * Has 4 different upgrades.
	 * Damage: 50
	 * Health: it needs health.
	 * CurrentLevel: The level that this tower has been upgraded to.
	 */
	public Catapult(Point location) {
		//Type, damage, radius, image, cost, sound, location
		super("Catapult", 100, 80, new Image("file:images/cannon.png"), 50, new Media(new File("sounds/Capture.mp3").toURI().toString()), location);
		super.setTowerType(ETower.catapult);
		
		AnimationTimer shootTimer = new AnimationTimer(){
				
			@Override
			public void handle (long now) {
				if((now - prev >= 0.05e9)) {
					prev = now;
					drawBall();
				}
			}
		};
			
		
		
		AnimationTimer attackTimer = new AnimationTimer(){
			
			@Override
			public void handle (long now) {
				if(first) {
					previous = now;
					first = false;
					//System.out.println("First call");
					//attackEnemy();
					//return;
				}
				if((now - previous >= 5.0e9)) {
					previous = now;
					
					shootTimer.start();
					
					shoot();
					drawBall();
				}
				
				if(shotIter == 20) {
					attack();
					//drawExplosion();
					shootTimer.stop();
					shotIter = 0;
				}
			}
		};
		attackTimer.start();
	}

	public void findSpot() {
		Random r = new Random();
		int randX = this.getRange()+r.nextInt(500-this.getRange());
		
		int randY = this.getRange()+r.nextInt(500-this.getRange());
		
		fireLoc = new Point(randX,randY);
	}
	
	/**
	 * Gets a list of enemies within this towers range and sets this list of enemies
	 * as this tower's target, dealing damage to each enemy.
	 */
	@Override
	public List<Enemy> getPrioEnemies(List<Enemy> enemyList)
	{
		List<Enemy> prios = this.getEnemyList();
		prios.clear();
		if(enemyList.isEmpty()) {
			return null;
		}
		for (Enemy en : enemyList) {
			Point enLoc = en.getLoc();
			int dist = (int) Math.sqrt(Math.pow(enLoc.getX() - fireLoc.getX(), 2) + Math.pow((enLoc.getY() - fireLoc.getY()), 2));
			if (dist < this.getRange() && en.canBeHit()) {
				prios.add(en);
			}
		}
		return prios;
	}
	
	public boolean canAttack() {
		return (this.getCurrentEnemy() != null);
	}
	
	
	/**
	 * Attacks this tower's target. Since this is an AOE tower, this will
	 * be a list of enemy entities that we can target and kill.
	 */
	@Override
	public boolean attack() {
		List<Enemy> ens = this.getEnemyList();
		if(ens.isEmpty())
			return false;
		
		shoot();
		for (Enemy en : ens) {
			en.setAttacked(true);
			en.setHel(en.getHel()-this.getDamage());
		}
		return true;
	}
	
	public void drawBall() {
		
		if(shotIter ==0) {
			prevBallX = this.getLocation().getX();
			prevBallY = this.getLocation().getY();
		}
	
		double ballX = (xDist/20) + prevBallX;
		double ballY = (xDist/20) + prevBallX;
		
		this.getGC().drawImage(ball, ballX, ballY, 20, 20);
		
		
		this.getGC().setGlobalAlpha(0.15);
		this.getGC().setFill(Color.RED);
		this.getGC().fillOval(fireLoc.getX()-this.getRange(), fireLoc.getY()-this.getRange(), this.getRange()*2, this.getRange()*2);
		this.getGC().setGlobalAlpha(1.0);
		this.getGC().setFill(Color.WHITE);
		
		prevBallX = ballX;
		prevBallY = ballY;
		
		shotIter++;
	}
	
	
	@Override
	public void shoot() {
		
		findSpot();
		
		xDist = (int)(fireLoc.getX() - this.getLocation().getX());
		yDist = (int)(fireLoc.getY() - this.getLocation().getY());
	}

	/**
	 * Upgrades the tower to the next level if not maxed out
	 */
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

	@Override
	public Enemy getPrioEnemy(List<Enemy> enemyList) {
		// TODO Auto-generated method stub
		return null;
	}
}
