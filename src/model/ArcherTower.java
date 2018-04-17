package model;

import java.awt.GraphicsConfigTemplate;
import java.awt.Point;
import java.io.File;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 * ArcherTower is another Tower type. 
 *
 */
public class ArcherTower extends Tower {

	/**
	 * Creates a new ArcherTower, using sound effects and 
	 * giving this tower a projectile.
	 * 
	 * Has 4 different upgrades.
	 * Damage: 50
	 * Health: it needs health.
	 * CurrentLevel: The level that this tower has been upgraded to.
	 */
	public ArcherTower(Point location) {
		super("Archer", 3, 200, 5, new Image("file:images/archer.png"), 50, new Media(new File("sounds/Capture.mp3").toURI().toString()), location);
		super.setTowerType(ETower.archer);
	}

	@Override
	public Enemy getPrioEnemy(List<Enemy> enemyList)
	{
		Enemy priority = null;
		if(enemyList.isEmpty()) {
			return null;
		}
		int closest = (int) Math.sqrt(Math.pow(enemyList.get(enemyList.size()-1).getLoc().getX() - this.getLocation().getX(), 2)
				            + Math.pow((enemyList.get(enemyList.size()-1).getLoc().getY() - this.getLocation().getY()), 2));
		if(closest < this.getRange() && enemyList.get(enemyList.size()-1).canBeHit())
			priority = enemyList.get(enemyList.size()-1);
		for (Enemy en : enemyList) {
			Point enLoc = en.getLoc();
			int dist = (int) Math.sqrt(Math.pow(enLoc.getX() - this.getLocation().getX(), 2) + Math.pow((enLoc.getY() - this.getLocation().getY()), 2));
			if (dist < this.getRange() && dist < closest && en.canBeHit()) {
				priority = en;
				closest = dist;
			}
		}

		return priority;
	}
	
	@Override
	public boolean attack() {
		if (this.getCurrentEnemy() == null)
			return false;
		
		this.getCurrentEnemy().setAttacked(true);
		this.getCurrentEnemy().setHel(this.getCurrentEnemy().getHel()-this.getDamage());
		this.setEnemy(null);
		return true;
	}

	@Override
	public boolean levelUp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Enemy> getPrioEnemies(List<Enemy> enemyList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void show(GraphicsContext gc)
	{
		//actual tower image
		gc.drawImage(this.getCurrentImage(), 0, 0, 60, 80, this.getLocation().getX()-30, this.getLocation().getY()-40, 60, 80);
		//actual tower location green box
		gc.drawImage(testing, this.getLocation().getX(), this.getLocation().getY());
		
	}
}
