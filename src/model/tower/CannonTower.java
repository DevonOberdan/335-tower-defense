package model.tower;

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

/**
 * ArcherTower is another Tower type. 
 *
 */
public class CannonTower extends Tower {

	
	private static boolean first = true;
	//private long start;
	private long previous;
	private int shotIter = 0;
	
	private long prev;
	private double prevBallX;
	private double prevBallY;
	private int yDist;
	private Point fireLoc;
	private Image ball = new Image("file:images/cannon_ball.png");
	private int xDist;
	
	/**
	 * Creates a new ArcherTower, using sound effects and 
	 * giving this tower a projectile.
	 * 
	 * Has 4 different upgrades.
	 * Damage: 50
	 * Health: it needs health.
	 * CurrentLevel: The level that this tower has been upgraded to.
	 */
	public CannonTower(Point location) {
		//Type, damage, radius, image, cost, sound, location
		super("Catapult", 100, 80, new Image("file:images/cannon.png"), 125, new Media(new File("sounds/Capture.mp3").toURI().toString()), location);
		super.setTowerType(ETower.catapult);
		findSpot();
		AnimationTimer shootTimer = new AnimationTimer(){
				
			@Override
			public void handle (long now) {
				if((now - prev >= 0.05e9)) {
					prev = now;
					drawBall();
				}
			}
		};
			
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle (long now) {
				if(first) {
					System.out.println("dyooooooo");
					previous = now;
					first = false;
				}
				if((now - previous >= 5.0e9) && !betweenRounds) {
					System.out.println("dffdfd");
					previous = now;
					shootTimer.start();
					shoot();
					drawBall();
				}
				if(shotIter == 20) {
					attack();
					shootTimer.stop();
					shotIter=0;
				}
			}
		};
		timer.start();
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
	
	public void findSpot() {
		Random r = new Random();
		
		int x = this.getRange()+r.nextInt(500 - this.getRange());
		int y = this.getRange()+r.nextInt(500 - this.getRange());
		
		fireLoc = new Point(x,y);
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
	    		if(en.canBeHit()) {
	    			en.setAttacked(true);
	    			en.setHel(en.getHel()-this.getDamage());
	    		}
	    }
	    return true;
	  }
	
	  public void drawBall() {
		    
		    if(shotIter ==0) {
		      prevBallX = this.getLocation().getX();
		      prevBallY = this.getLocation().getY();
		    }
		  
		    double ballX = (xDist/20) + prevBallX;
		    double ballY = (yDist/20) + prevBallY;
		    
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
			gc.drawImage(this.getCurrentImage(), 0, 0, 150, 170, this.getLocation().getX()-30, this.getLocation().getY()-40, 60, 80);
			if(this.getSelected()) {
				gc.setGlobalAlpha(0.15);
				gc.setFill(Color.GHOSTWHITE);
				gc.fillRect(0, 0, 500, 500);
				//gc.fillOval(0,0, 540, 540);
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