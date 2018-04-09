package model;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public abstract class Tower {
	protected float    range;
	protected int      damage;
	protected int      xp;
	protected int      currentLevel;
	protected int[]    upgradeCosts;
	
	protected Image[]  images;
	protected Image[]  projectiles;
	protected Media    soundEffect;
	
	public float  getRange()             { return range;        }
	public int    getDamage()            { return damage;       }
	public int    getXP()                { return xp;           }
	public int    getLevel()             { return currentLevel; }
	public int    getCost()              { return upgradeCosts[currentLevel]; }
	
	public Image  getCurrentImage()      { return images[currentLevel];      }
	public Image  getCurrentProjectile() { return projectiles[currentLevel]; }
	public Media  getSoundEffect()       { return soundEffect;               }
	
}
