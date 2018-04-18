package model;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
public class MultiTower extends Tower{

	/**
	 * Creates a new ArcherTower, using sound effects and 
	 * giving this tower a projectile.
	 * 
	 * Has 4 different upgrades.
	 * Damage: 50
	 * Range: 200
	 * CurrentLevel: The level that this tower has been upgraded to.
	 */
	public MultiTower(Point location) {
		super("Multi", 1, 100, 2, new Image("file:images/MultiTower1.png"), 75, new Media(new File("sounds/Capture.mp3").toURI().toString()), location);
		super.setTowerType(ETower.area);
	}

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
	@Override
	public boolean attack() {
		List<Enemy> ens = this.getEnemyList();
		if(ens.isEmpty())
			return false;
		for (Enemy en : ens) {
			en.setAttacked(true);
			en.setHel(en.getHel()-this.getDamage());
		}
		return true;
	}

	@Override
	public boolean levelUp() {
		this.setDamage((int)(this.getDamage() * 0.65));
		this.setRange((int)(this.getRange() * 1.5));
		this.increaseLevel();
		switch(this.getLevel()) {
		case 2:
			this.setImage(new Image("file:images/MultiTower2.png"));
			break;
		case 3:
			this.setImage(new Image("file:images/MultiTower3.png"));
			break;
		default:
			//NO LEVEL FOR U
		}
		return false;
	}

	public void show(GraphicsContext gc)
	{
		//actual tower image
		gc.drawImage(this.getCurrentImage(), 0, 0, 150, 150, this.getLocation().getX()-30, this.getLocation().getY()-40, 60, 80);
		//actual tower location green box
		gc.drawImage(testing, this.getLocation().getX(), this.getLocation().getY());
		
	}
	
	@Override
	public Enemy getPrioEnemy(List<Enemy> enemyList) {
		return null;
	}
}


