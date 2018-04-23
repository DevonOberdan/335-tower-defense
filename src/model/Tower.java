 package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
	private int 	 cost;
	private ETower   towerType;
	private Image  	 image;
	private Image    projectile;
	private Media    soundEffect;
	private Point 	 TowerLocation;
	private boolean isSelected;
	private List<Enemy> ens;
	private Enemy enemy;
	
	protected final Image testing = new Image("file:images/testing.png") ;
	
	/**
	 * Creates a new Tower object. 
	 * @param name name of the tower (useless!)
	 * @param damage how much dmg it does to enemies
	 * @param radius the range of this tower
	 * @param fireRate how quickly this tower fires
	 * @param image what the tower looks like
	 * @param cost how much it costs to purchase this tower
	 * @param soundeff the sound effects of this tower
	 * @param location the location on the scene of this tower.
	 */
	public Tower (String name, int damage, int radius, Image image, int cost, Media soundeff, Point location) {
		this.towerName = name;
		this.radius = radius;
		this.damage = damage;
		this.image = image;
		this.cost = cost;
		this.currentLevel = 1;
		this.soundEffect = soundeff;
		this.TowerLocation = location;
		this.enemy = null;
		this.isSelected = false;
		ens = new ArrayList<>();
	}
	/**
	 * To be implemented later... I have a few ideas for this.
	 * @return
	 */
	public abstract boolean attack(GraphicsContext gc);
	
	/**
	 * Levels up this specific tower. Different for other tower types.
	 * Needs to be implemented in the class that extends Tower.
	 * Could have subset functions of changing the tower image to a higher
	 * tier, different name, etc.
	 * @return
	 */
	public abstract boolean levelUp();
	
	public String getName()					{ return towerName; 	} 	//name of tower
	public int	  getRange()					{ return radius;       } //range of tower
	public int    getDamage()				{ return damage;       } //damage of tower
	public int    getXP()              		{ return xp;           } //current tower xp ?
	public int    getLevel()             	{ return currentLevel; } //current tower level
	public int    getCost()              	{ return (int) (cost * (currentLevel * 1.5)); } //current cost of tower
	public ETower getTowerType()				{ return this.towerType; } //type of tower
	public int getX()						{ return (int)this.TowerLocation.getX(); } //location of x on board
	public int getY()						{ return (int)this.TowerLocation.getY(); } //location of y on board
	public Image  getCurrentImage()      	{ return image;        } //image of the tower
	public Image  getCurrentProjectile() 	{ return projectile;   } //projectile image
	public Media  getSoundEffect()       	{ return soundEffect;  } //sound effects of this tower when it shoots
	public Point  getLocation()          	{ return TowerLocation;} //location of tower
	public void setSelected(boolean b) 		{ this.isSelected = b; } //is this tower selected?
	public boolean getSelected() 			{ return this.isSelected; } //is this tower selected?
	public Enemy getCurrentEnemy()    		{ return enemy;   } //current target
	public List<Enemy> getEnemyList() 		{ return ens; } //current targets
	public void setDamage(int num)			{ this.damage = num; 	} //set damage
	public void setRange(int num)			{ this.radius = num;	} //set range
	public void setSoundEffect(Media sound) 	{ this.soundEffect = sound; } //set sound effect.
	public boolean setTowerType(ETower type) { this.towerType = type; return true;} //set tower type.
	
	/**
	 * Sets this tower's image to image.
	 * @param image
	 * @return
	 */
	public boolean setImage(Image image) { this.image = image; return true; } //sets the image 
	/**
	 * Levels up this tower!
	 * @return
	 */
	public boolean increaseLevel() { this.currentLevel++; return true;}	//increments tower level.
	
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
