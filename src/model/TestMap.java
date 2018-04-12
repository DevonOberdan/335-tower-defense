package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
/**
 * TestMap exhibits the nature of an actual game that we might
 * end up playing. Contains several tests including the drawing
 * of animations, spawning of enemies and towers, and the targeting 
 * of each tower for a new enemy.
 * 
 * The basis of this class is to test the functionality of whether or not
 * a tower can target an enemy, shoot and kill them, and then target
 * the next enemy that is farthest along the path.
 * 
 */
public class TestMap extends Map {
	private Path path; //Path that each enemy travels.
	private List <Enemy> enemyList; //List of enemies to be drawn and targetted.
	private List <Tower> towerList; //List of towers that are placed on the map.
//	private List <Tower> availableTowers; //Available towers that we can select from the menu on the right.
	// ^^^^^^^ Needs to be implemented somehow. 
	private final Image background = new Image("file:images/map_1.jpg");
	private final Image menuBar = new Image("file:images/menu.jpg");
	
	private GraphicsContext gc;
	private Timeline timeline;
	
	/**
	 * Creates a testmap. This constructor will initialize each of our
	 * lists; enemies, towers, and creates the timeline for animating the
	 * background, and the enemies on it.
	 * 
	 * @param gc the graphics context in which we draw upon. THE EISEL FOR 
	 * ALL OF MY CREATIVITY AND FRUITINESS
	 */
	public TestMap(GraphicsContext gc) {
		super(gc);
		this.gc = gc;
		path = new Path();
		enemyList = new LinkedList<>();
		towerList = new ArrayList<>();
		timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter())); 
		 timeline.setCycleCount(Animation.INDEFINITE);
	}
	
	/**
	 * Takes in an integer as a parameter, and adds this many enemies
	 * to a list. This functions spawns in ALL ENEMIES. Including the ones 
	 * spawned after each wave. Each wave is an illusion. They have already been
	 * spawned. This may be refactored, but for testmap, it's a working solution.
	 * 
	 * We need to implement some sort of counter that tells us how many times we have
	 * killed an enemy; this will allow us to determine what wave we are on, and then spawn
	 * in those enemies after we have killed everyone from the first round.
	 * 
	 * @param enemyCount
	 */
	public void spawnEnemies(int enemyCount) {
		for (int i=0; i<enemyCount; i++) {
			Enemy enemy; 
			if( i >= 5 ) { //Trying to introduce 'waves'
				enemy = new WolfEnemy(new Point(((i*75 + 1000)), 0), 2, path);
				enemyList.add(enemy);
			} else {
				enemy = new WolfEnemy(new Point(((i*75)), 0), 2, path);
				enemyList.add(enemy);
			}
			enemy.setHel(100);
		}
		System.out.printf("%d enemies have been spawned.\n", enemyCount);
	}
	
	private class AnimateStarter implements EventHandler<ActionEvent> {
		private int tic=0;
		@Override
		public void handle(ActionEvent event) {
			gc.clearRect(0, 0, 580, 500);
			gc.drawImage(menuBar, 0, 0);
			gc.drawImage(background, 0, 0);
			
			if (tic == 4)
				tic=0;
			for (Tower t : towerList) { 
				/* WolfEnemy e = (WolfEnemy) t.getPrioEnemy(enemyList);
				if(e != null && e.getHel() < 1) {
					enemyList.remove(e);
				}
				t.setEnemy(e);
				t.attack();
				*/
				if(!enemyList.isEmpty()) {
					t.setEnemy(null);
					WolfEnemy e = (WolfEnemy) enemyList.get(0);
					if(e != null && e.getHel() < 1 && !enemyList.isEmpty()) {
						enemyList.remove(0);
						e = (WolfEnemy) enemyList.get(0);
					}
					t.setEnemy(e);
					if(e != null) {
						t.attack();
						e.setAttacked(true);
					}
				}
				
				t.show(gc);
			}
			for (Enemy e : enemyList) {
				((WolfEnemy) e).show(gc, tic);
				e.setAttacked(false);
			}
			tic++;
		}
		
	}
	
	public void addTower(Point p) {
		System.out.println("Tower added @"+p);
		towerList.add(new ArcherTower(p));
	}
	
	
	public void show() {
		timeline.play();
	}
	
	
	public Path getPath() {
		System.out.println("Map: returned path");
		return this.path;
	}
}
