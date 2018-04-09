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
<<<<<<< HEAD
		super("Archer", 25, 50, 1, new Image("file:images/tower.png"), 50, new Media(new File("sounds/Capture.mp3").toURI().toString()));
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
=======
		this.range  = (float) 50.0;
		this.damage = 25;
		this.xp     =  0;
		this.currentLevel = 0;
		this.upgradeCosts = new int[4];
		
		upgradeCosts[0] = 50;
		upgradeCosts[1] = 150;
		upgradeCosts[2] = 200;
		upgradeCosts[3] = 200;
				
		this.images      = new Image[4];
		images[0]        = new Image("file:images/archer.png",false);
		this.projectiles = new Image[4];
		this.soundEffect = new Media(new File("sounds/Capture.mp3").toURI().toString());
>>>>>>> 0ba30afce7fc72d75f6a98745b12636cc1b5abdd
	}
}
