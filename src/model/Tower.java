 package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public abstract class Tower {
	
	private String   towerName;
	private int      radius; //range
	private int      damage;
	private int      xp;
	private int      currentLevel;
	private int		 fireRate;
	private int 	     cost;
	private ETower   towerType;
	private Image  	 image;
	private Image    projectile;
	private Media    soundEffect;
	private Point 	 TowerLocation;
	private NewEnemy enemy;
	
	private final Image testing = new Image("file:images/testing.png") ;
	
	public Tower (String name, int damage, int radius, int fireRate, Image image, int cost, Media soundeff, Point location) {
		this.towerName = name;
		this.radius = radius;
		this.damage = damage;
		this.fireRate = fireRate;
		this.image = image;
		this.cost = cost;
		this.currentLevel = 1;
		this.soundEffect = soundeff;
		this.TowerLocation = location;
		this.enemy = null;
	}
	/**
	 * To be implemented later... I have a few ideas for this.
	 * @return
	 */
	public abstract boolean AttackEnemy();
	
	/**
	 * Levels up this specific tower. Different for other tower types.
	 * Needs to be implemented in the class that extends Tower.
	 * Could have subset functions of changing the tower image to a higher
	 * tier, different name, etc.
	 * @return
	 */
	public abstract boolean levelUp();
	
	public String getName()				 { return towerName; 	}
	public int	  getRange()			 { return radius;       }
	public int    getDamage()            { return damage;       }
	public int    getXP()                { return xp;           }
	public int    getLevel()             { return currentLevel; }
	public int    getCost()              { return (int) (cost * (currentLevel * 0.85)); }
	public ETower getTowerType()		 { return this.towerType; }
	
	public Image  getCurrentImage()      { return image;        }
	public Image  getCurrentProjectile() { return projectile;   }
	public Media  getSoundEffect()       { return soundEffect;  }
	public Point  getLocation()          { return TowerLocation;}
	public NewEnemy getCurrentEnemy()    { return enemy;   }
	public boolean setTowerType(ETower type) {
		this.towerType = type;
		return true;
	}
	
	public boolean setImage(Image image) {
		this.image = image;
		return true;
	}
	
	public boolean increaseLevel() {
		this.currentLevel++;
		return true;
	}
	
	
/*	public int damageTaken(Enemy enemy) {
		Point enLoc = enemy.getLocation();
		int dist = Math.abs((int) Math.sqrt(Math.pow(enLoc.getX() - this.TowerLocation.getX(), 2) + Math.pow((enLoc.getY() - this.TowerLocation.getY()), 2)));
		if (dist < this.radius) {
			return damage;
		}
		return 0;
	}
	*/
	
	/**
	 * gets the highest valued enemy (the enemy that has traveled the farthest
	 * down the path) for this turret and returns that enemy for the 
	 * turret to shoot.
	 * @return priority enemy
	 */
	public NewEnemy getPrioEnemy(List<NewEnemy> enemyList) {
		NewEnemy priority = null;
		int closest = Integer.MAX_VALUE;
		if(enemyList.isEmpty()) {
			return null;
		}
		
		for (NewEnemy en : enemyList) {
			Point enLoc = en.getLoc();
			int dist = (int) Math.sqrt(Math.pow(enLoc.getX() - this.TowerLocation.getX(), 2) + Math.pow((enLoc.getY() - this.TowerLocation.getY()), 2));
			if (dist < this.radius && dist < closest) {
				priority = en;
				closest = dist;
			}
		}
		if(priority != null) {
			priority.setAttacked(true);
		}
		return priority;
	}
	public void show(GraphicsContext gc) {
		//actual tower image
		gc.drawImage(image, 0, 0, 60, 80, TowerLocation.getX()-30, TowerLocation.getY()-40, 60, 80);
		//actual tower location green box
		gc.drawImage(testing, TowerLocation.getX(), TowerLocation.getY());
		
	}
	public void setEnemy (NewEnemy e) {
		enemy = e;
	}
	public void attack() {
		if (enemy == null)
			return;
		if (enemy.getHel() < 1) {
			enemy = null;
			return;
		}
		if(!enemy.withenRange(this)) {
			enemy.setAttacked(false);
			enemy = null;
			return;
		}
		enemy.setAttacked(true);
		enemy.setHel(enemy.getHel()-damage);
		enemy = null;
	}
	
}
