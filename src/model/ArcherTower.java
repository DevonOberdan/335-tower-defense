package model;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class ArcherTower extends Tower {

	
	public ArcherTower() {
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
		this.projectiles = new Image[4];
		this.soundEffect = new Media(new File("sounds/Capture.mp3").toURI().toString());
	}
}
