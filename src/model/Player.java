package model;

 
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {

	private int health;
	private int gold;
	private List <Tower> towerList;
	private GraphicsContext gc;
	private final Image goldImg = new Image("file:images/menu/coin_sprite.png");
	private final Image healthImg = new Image("file:images/menu/heart_sprite.png");
	private int healthC = 0, goldC = 0;
	
	public Player(GraphicsContext gc,int health, int gold)
	{
		this.health = health;
		this.gold = gold;
		this.gc = gc;
		towerList = new ArrayList<>();
	}
	
	public void addTower(Tower t)	{ towerList.add(t); }
	
	public int getHealth()			{ return health;		}
	public int getGold()				{ return gold;		}
	public List<Tower> getTowers()   { return towerList; }
	
	/**
	 * 
	 * @param damage
	 * @return
	 */
	public boolean doDamage(int damage) {
		if (health <= 0) {
			return false;
		}
		this.health -= damage;
		return true;
	}
	/**
	 * 
	 * @param amt
	 */
	public void deposit(int amt) {
		this.gold += amt;
	}
	
	/**
	 * 
	 * @param amt
	 * @return
	 */
	public boolean withdraw(int amt) {
		if(this.gold <= 0)
			return false;
		this.gold -= amt;
		return true;
	}

	
	public void draw() 
	{
		if (healthC == 7)			{ healthC = 0; 		}
		if (goldC == 6)
		{
			goldC = 0;
		}
		
		gc.drawImage(goldImg, 60*goldC, 0, 60, 60, 510, 20, 20, 20);
		gc.fillText(""+gold, 535, 35);
		gc.drawImage(healthImg, 60*healthC, 0, 60, 60, 510, 55, 20, 20);
		gc.fillText(""+health, 535, 70);
		
		goldC++;
		healthC++;
	}
	
}
