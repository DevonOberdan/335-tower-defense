package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
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
public class IceMap extends Map {
//	private List <Tower> availableTowers; //Available towers that we can select from the menu on the right.
	// ^^^^^^^ Needs to be implemented somehow. 
	
	private Timeline timeline; //The animator-2000.
	private Point start;
	
	/**
	 * Creates a testmap. This constructor will initialize each of our
	 * lists; enemies, towers, and creates the timeline for animating the
	 * background, and the enemies on it.
	 * 
	 * @param gc the graphics context in which we draw upon. THE EISEL FOR 
	 * ALL OF MY CREATIVITY AND FRUITINESS
	 */
	public IceMap(GraphicsContext gc) {
		super();
		background = new Image("file:images/maps/map_5.png");
		menuBar = new Image("file:images/menu.jpg");
		this.gc = gc;
		enemyList = new LinkedList<>();
		towerList = new ArrayList<>();
		timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter())); 
		 timeline.setCycleCount(Animation.INDEFINITE);
		 start = new Point (375, 500);
		 path = new IcePath();
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
				Point offset = new Point(0, -((i*75 + 1000)));
				enemy = new TestEnemy(2, path, new Point((int) (start.getX() - offset.getX()), (int ) (start.getY() - offset.getY())));
				enemyList.add(enemy);
			} else {
				Point offset = new Point(0, -((i*75)));
				enemy = new TestEnemy(2, path, new Point((int) (start.getX() - offset.getX()), (int)(start.getY() - offset.getY())));
				enemyList.add(enemy);
			}
			enemy.setHel(100);
		}
		System.out.printf("%d enemies have been spawned.\n", enemyCount);
	}
	/**
	 * Private handler for timeline that will target an enemy for each tower, and
	 * animate each object that we have placed on the map. THis is where
	 * all of the animating, targeting, and logic of object interactions takes place.
	 * 
	 * Will likely need some sort of refactoring and thought to make it more
	 * OOP-y, but this works.
	 *
	 */
	private class AnimateStarter implements EventHandler<ActionEvent> {
		private int tic=0;
		@Override
		public void handle(ActionEvent event) {
			gc.clearRect(0, 0, 580, 500);
			gc.drawImage(menuBar, 0, 0);
			gc.drawImage(background, 0, 0);
			
			for (Tower t : towerList) { 
				/* TestEnemy e = (TestEnemy) t.getPrioEnemy(enemyList);
				if(e != null && e.getHel() < 1) {
					enemyList.remove(e);
				}
				t.setEnemy(e);
				t.attack();
				*/
				if(!enemyList.isEmpty()) {
					t.setEnemy(null);
					TestEnemy e = (TestEnemy) enemyList.get(0);
					if(e != null && e.getHel() < 1 && !enemyList.isEmpty()) {
						enemyList.remove(0);
						e = (TestEnemy) enemyList.get(0);
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
				((TestEnemy) e).show(gc);
				e.setAttacked(false);
			}
		}
		
	}
	/**
	 * Adds a new archerTower onto the screen at position p.
	 */
	public void addTower(Point p) {
		System.out.println("Tower added @"+p);
		towerList.add(new ArcherTower(p));
	}
	
	/**
	 * PLays the timeline, and ultimately plays the game!
	 */
	public void show() {
		timeline.play();
	}

	@Override
	public int getEnemyCount() {
		return enemyList.size();
	}

	@Override
	public boolean isRunning() {
		return getEnemyCount() > 0;
	}

	@Override
	public Canvas getCanvas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphicsContext getGC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enemy> getEnemyList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tower> getTowerList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path getPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
