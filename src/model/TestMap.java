package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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
 * the next enemy that is furthest along the path.
 * 
 */
public class TestMap extends Map {
	private Path path; //Path that each enemy travels.
	private List <NewEnemy> enemyList; //List of enemies to be drawn and targetted.
	private List <Tower> towerList; //List of towers that are placed on the map.
	private List <Tower> availableTowers; //Available towers that we can select from the menu on the right.
	// ^^^^^^^ Needs to be implemented somehow. use brain u idiot aka taite
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
		this.gc = super.getGC();
		path = new Path();
		enemyList = new ArrayList<>();
		towerList = new ArrayList<>();
		timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter())); 
		 timeline.setCycleCount(Animation.INDEFINITE);
	}
	
	/**
	 * Adds
	 * @param enemyCount
	 */
	public void spawnEnemies(int enemyCount) {
		for (int i=0; i<enemyCount; i++) {
			enemyList.add(i, new NewEnemy(new Point(i*50, 0), 2, path));
		}
		System.out.printf("%d enemies have been spawned.\n", enemyCount);
	}
	
	private class AnimateStarter implements EventHandler<ActionEvent> {
		private int tic=0, img=0;
		@Override
		public void handle(ActionEvent event) {
			gc.clearRect(0, 0, 580, 500);
			gc.drawImage(menuBar, 0, 0);
			gc.drawImage(background, 0, 0);
			
			if (img == 4)
				img=0;
			for (Tower t : towerList) {
				NewEnemy e = t.getPrioEnemy(enemyList);
				t.setEnemy(e);
				t.attack();
				t.show(gc);
			}
			for (NewEnemy e : enemyList) {
				e.show(gc, img);
				e.setAttacked(false);
			}
			img++;
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
