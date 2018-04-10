package model;

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
	public ArcherTower() {
		super("Archer", 25, 100, 1, new Image("file:images/archer.png"), 50, new Media(new File("sounds/Capture.mp3").toURI().toString()));
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
