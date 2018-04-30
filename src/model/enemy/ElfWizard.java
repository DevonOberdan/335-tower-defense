package model.enemy;

import java.awt.Point;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Path;

/**
 * Enemy subclass that represents the Skeleton Enemy type, with its
 * specific attributes.
 * 
 * @author Devon Oberdan
 *
 */ 
public class ElfWizard extends Enemy{
	
	// data structures built here to be passed into super constructor
	
	private final static int speed = 5;
	private final static int health = 100;
	private final static int damage = 15;
	private final static int reward = 25;
	
	private int spellIter=0;
	
	private final static int range = 100;
	
	private ArrayList<Enemy> neighborList;
	
	private static Point walkDims = new Point(416,473);
	private static Point deathDims = new Point(350,330);
	
	private static String[] elfWizard = new String[]{"file:images/enemies/elfWizard/elf_wizard_right.png",
											     "file:images/enemies/elfWizard/elf_wizard_left.png"};
	
	private static String[] deadElfWizard = new String[] {"file:images/enemies/elfWizard/dead_elf_wizard_right.png", 
											   		   "file:images/enemies/elfWizard/dead_elf_wizard_left.png"};
	
	
	private Image[] spell = new Image[] {new Image("file:images/spell/spellhealth-spell.png"),new Image("file:images/spell/health-spell1.png"),
										new Image("file:images/spell/health-spell2.png"),new Image("file:images/spell/health-spell3.png")};
	
	/**
	 * Enemy constructor called by program, which then sends specific info to the super Enemy class.
	 * @param path
	 * @param start
	 */
	public ElfWizard(Path path, Point start) {
		//speed, health, walkImageDimensions, deathImageDimensions, walkFrames, deathFrames, walkFiles, deathFiles, path, startPoint
		super(speed, health, damage, reward, walkDims, deathDims, 5, 5, elfWizard, deadElfWizard, path, start);
		neighborList = new ArrayList<Enemy>();
	}	
	
	public void getPrioEnemies() {
		neighborList.clear();
		for (Enemy en : enList) {
			Point enLoc = en.getLoc();
			int dist = (int) Math.sqrt(Math.pow(enLoc.getX() - this.getLoc().getX(), 2) + Math.pow((enLoc.getY() - this.getLoc().getY()), 2));
			System.out.println(dist+" die");
			if (dist < range && en.canBeHit() && !en.equals(this)) neighborList.add(en);
		}
	}
	
	@Override
	public void show(GraphicsContext gc) {
		getPrioEnemies();
		healNeighbors();
		System.out.println(neighborList.size());

		if(neighborList.size() != 0) {
			//actual tower image
//				gc.setGlobalAlpha(0.15);
//				gc.setFill(Color.LIMEGREEN);
//				gc.fillOval(this.getLoc().getX()-range, this.getLoc().getY()-range, range*2, range*2);
//				gc.setGlobalAlpha(1.0);
			gc.drawImage(spell[spellIter], this.getLoc().getX()-range, this.getLoc().getY()-range, range*2,range*2);
		}
		spellIter++;
		if(spellIter==3) spellIter=0;
		super.show(gc);
	}
	
	public void healNeighbors() {		
		for(Enemy en: neighborList) {
			if(en.getHel() < en.maxHealth)
				en.setHel(en.getHel() + 1);
		}
	}
	
	/**
	 * Method to draw health bar right above the Enemy.
	 * 
	 * @author Devon Oberdan
	 */
	@Override
	public void drawHealthBar(GraphicsContext gc) {
		double currentHealth = (imgWidth*0.6)*healthPerc;
		
		int xShift = 10;
		
		// draw bar to show current health
		gc.setFill(Color.LIME);
		gc.fillRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2)-5, currentHealth, 4);

		// draw border to show the full health amount
		gc.setStroke(Color.BLACK);		
		gc.strokeRect(loc.getX()-(imgWidth/2)+xShift, loc.getY()-(imgHeight/2)-5, imgWidth*0.6, 4);
	}
}