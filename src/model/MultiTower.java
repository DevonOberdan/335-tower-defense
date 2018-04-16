package model;
import java.awt.Point;
import java.io.File;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
public class MultiTower extends Tower{

	/**
	 * Creates a new ArcherTower, using sound effects and 
	 * giving this tower a projectile.
	 * 
	 * Has 4 different upgrades.
	 * Damage: 50
	 * Health: it needs health.
	 * CurrentLevel: The level that this tower has been upgraded to.
	 */
	public MultiTower(Point location) {
		super("Multi", 1, 200, 2, new Image("file:images/archer.png"), 50, new Media(new File("sounds/Capture.mp3").toURI().toString()), location);
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
	public boolean AttackEnemy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean levelUp() {
		// TODO Auto-generated method stub
		return false;
	}
}


