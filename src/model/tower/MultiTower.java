package model.tower;
import java.awt.Point;
import java.io.File;
import java.io.Serializable;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import model.enemy.Enemy;

/**
 * Creates a multiTower object. This class extends tower
 * Don't really have much else to say. Read the code. Comments
 * are useless.
 */
public class MultiTower extends Tower implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5199044902617464469L;
	private static boolean first = true;
	private long previous;
	private long FIRERATE;
	AnimationTimer timer;
	/**
	 * Creates a new multi-area tower that will become 
	 * 
	 * Has 4 different upgrades.
	 * Damage: 5
	 * Range: 200
	 * CurrentLevel: The level that this tower has been upgraded to.
	 */
	public MultiTower(Point location) {
		super("Multi", 5, 100, new Image("file:images/multi1.png"), 75, new Media(new File("sounds/Capture.mp3").toURI().toString()), location, "inferno.mp3");
		super.setTowerType(ETower.area);
		this.FIRERATE = (long) 0.5e9;
		
		timer = new AnimationTimer(){
			
			@Override
			public void handle (long now) {
				if(first) {
					previous = now;
					first = false;
				}
				if((now - previous >= FIRERATE) && canAttack()) {
					previous = now;
					//System.out.println("one second later");
					attack();
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
			int dist = (int) Math.sqrt(Math.pow(enLoc.getX() - this.getLocation().getX(), 2) + Math.pow((enLoc.getY() - this.getLocation().getY()), 2));
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
		this.playEffect();
		shoot();
		for (Enemy en : ens) {
			en.setAttacked(true);
			en.setHel(en.getHel()-this.getDamage());
		}
		return true;
	}
	
	@Override
	public void shoot() {
		this.getGC().setGlobalAlpha(0.15);
		this.getGC().setFill(Color.RED);
		this.getGC().fillOval(this.getLocation().getX()-this.getRange(), this.getLocation().getY()-this.getRange(), this.getRange()*2, this.getRange()*2);
		this.getGC().setGlobalAlpha(1.0);
	}
	
	@Override
	public int getUpgradeCost() {
		return 275 * (this.getLevel());
	}
	
	@Override
	public boolean levelUp() {
		if(this.getLevel() == 3)
			return false;
		this.setDamage((int)(this.getDamage() * 1.5));
		this.setRange((int)(this.getRange() * 1.5));
		this.increaseLevel();
		switch(this.getLevel()) {
		case 2:
			this.FIRERATE = (long) 0.4e9;
			this.setImage(new Image("file:images/multi2.png"));
			break;
		case 3:
			this.FIRERATE = (long) 0.25e9;
			this.setImage(new Image("file:images/multi3.png"));
			break;
		default:
			//NO LEVEL FOR U
		}
		return false;
	}

	public void show(GraphicsContext gc)
	{
		//actual tower image
		gc.drawImage(this.getCurrentImage(), this.getLocation().getX()-30, this.getLocation().getY()-40, 60, 80);
		if(this.getSelected()) {
			gc.setGlobalAlpha(0.15);
			gc.setFill(Color.GHOSTWHITE);
			gc.fillOval(this.getLocation().getX()-this.getRange(), this.getLocation().getY()-this.getRange(), this.getRange()*2, this.getRange()*2);
			gc.setGlobalAlpha(1.0);
		}
		//gc.drawImage(testing, this.getLocation().getX(), this.getLocation().getY());
		
	}
	
	@Override
	public Enemy getPrioEnemy(List<Enemy> enemyList) {
		return null;
	}
	
	@Override
	public void endTimers() { timer.stop(); }
}


