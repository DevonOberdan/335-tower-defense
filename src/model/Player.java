package model;

 
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.enemy.Enemy;
import model.tower.Tower;

public class Player extends BorderPane {

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
	public boolean deposit(int amt, Enemy e) {
		if (!e.getAttackedPlayer())
			this.gold += amt;
		return true;
	}
	
	/**
	 * 
	 * @param amt
	 * @return
	 */
	public boolean withdraw(int amt) {
		if(this.gold-amt < 0)
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
		gc.setFill(Color.GHOSTWHITE);
		gc.drawImage(goldImg, 60*goldC, 0, 60, 60, 510, 0.1, 20, 20);
		gc.fillText(""+gold, 535, 12.5);
		gc.drawImage(healthImg, 60*healthC, 0, 60, 60, 510, 17.5, 20, 20);
		gc.fillText(""+health, 535, 33);
		
		goldC++;
		healthC++;
	}

	public boolean isDead() {
		if(getHealth() <= 0) {
			return true;
		}
		return false;
	}
	
}
