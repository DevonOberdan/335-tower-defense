package model;

import java.awt.Point;
import java.io.File;

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
		super("Archer", 3, 50, 1, new Image("file:images/archer.png"), 50, new Media(new File("sounds/Capture.mp3").toURI().toString()), location);
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
