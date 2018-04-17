 package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 * 
 * @author Taite Nazifi
 *
 */
public abstract class Tower {
	
	private String   towerName;
	private int      radius; //range
	private int      damage;
	private int      xp;
	private int      currentLevel;
	private int		 fireRate;
	private int 	 cost;
	private ETower   towerType;
	private Image  	 image;
	private Image    projectile;
	private Media    soundEffect;
	private Point 	 TowerLocation;
	private List<Enemy> ens;
	private Enemy enemy;
	
	protected final Image testing = new Image("file:images/testing.png") ;
	
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
		ens = new ArrayList<>();
	}
	/**
	 * To be implemented later... I have a few ideas for this.
	 * @return
	 */
	public abstract boolean attack();
	
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
	public Enemy getCurrentEnemy()    { return enemy;   }
	public List<Enemy> getEnemyList() { return ens; }
	public void setDamage(int num)		 { this.damage = num; 	}
	public void setRange(int num)		 { this.radius = num;	}
	public void setSoundEffect(Media sound) { this.soundEffect = sound; }
	
	public boolean setTowerType(ETower type) {
		this.towerType = type;
		return true;
	}
	
	
	/**
	 * Sets this tower's image to image.
	 * @param image
	 * @return
	 */
	public boolean setImage(Image image) {
		this.image = image;
		return true;
	}
	
	/**
	 * Levels up this tower!
	 * @return
	 */
	public boolean increaseLevel() {
		this.currentLevel++;
		return true;
	}
	
	/**
	 * gets the highest valued enemy (the enemy that has traveled the farthest
	 * down the path) for this turret and returns that enemy for the 
	 * turret to shoot.
	 * @return priority enemy
	 */
	public abstract Enemy getPrioEnemy(List<Enemy> enemyList);
	public abstract List<Enemy> getPrioEnemies(List<Enemy> enemyList);
	
	/**
	 * Draws this tower on the board at this position.
	 * @param gc
	 */
	public abstract void show(GraphicsContext gc); 
	/**
	 * Sets this tower's enemy target to e.
	 * @param e
	 */
	public void setEnemy (Enemy e) {
		enemy = e;
	}
	
}
