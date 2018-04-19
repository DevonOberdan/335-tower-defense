package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.util.Duration;
import view.WelcomeView;
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
	private Alert alert;
		
	private Image background; //background of the map
	private Image menuBar; //Menu bar; where we select different enemies.
	
	private Canvas canvas; //The canvas upon which I lay all of my brilliant ideas upon
	private GraphicsContext gc; //graphics context in which the canvas actually gets drawn.
	private List<Enemy> enemyList; //List of enemies
	private List<Tower> towerList; //List of towers
	private Path path; //Path that the enemies must travel in.
	private Player player;
	
	/**
	 * Creates a testmap. This constructor will initialize each of our
	 * lists; enemies, towers, and creates the timeline for animating the
	 * background, and the enemies on it.
	 * 
	 * @param gc the graphics context in which we draw upon. THE EISEL FOR 
	 * ALL OF MY FRUITY CREATIVENESS
	 */
	public IceMap(Player p, GraphicsContext gc) {
		super();
		background = new Image("file:images/maps/map_5.png");
		menuBar = new Image("file:images/menu.jpg");
		this.gc = gc;
		enemyList = new LinkedList<>();
		towerList = new ArrayList<>();
		this.player = p;
		timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter())); 
		 timeline.setCycleCount(Animation.INDEFINITE);
		 start = new Point (375, 500);
		 path = new IcePath();
		 alert = new Alert(AlertType.INFORMATION);
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
				enemy = new WolfEnemy(path, new Point((int) (start.getX() - offset.getX()), (int ) (start.getY() - offset.getY())));
				enemyList.add(enemy);
			} else {
				Point offset = new Point(0, -((i*75)));
				enemy = new WolfEnemy(path, new Point((int) (start.getX() - offset.getX()), (int)(start.getY() - offset.getY())));
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
		@Override
		public void handle(ActionEvent event) {
			gc.clearRect(0, 0, 580, 500);
			gc.drawImage(menuBar, 0, 0);
			gc.drawImage(background, 0, 0);
			
			updateAndReassignTowers();
			
			for (Enemy e : enemyList) {
				if(e.getDeathTicker() >= e.deathFrameCount()) {
					e = enemyList.get(0);
				}
				if(e != null) {
					e.show(gc);
					e.setAttacked(false);
					if (!e.getDead() && e.attackPlayer(player))
						e.setDead();
				}
				checkGameOver(player);
			}
			enemyList.removeIf(e -> (e.getDeathTicker() >= e.deathFrameCount() && player.deposit(30)));
			if(!isRunning()) {
				
				endRound();
			}
		}
		
	}
	
	
	public void endRound() {
		timeline.stop();
		alert.setTitle("GAME OVER");
		alert.setHeaderText(null);
		alert.setContentText("You've defeated the Legion! :-)\nClick OK, then click the screen to advance to the\nnext stage of the game.");
		alert.show();
	}
	/**
	 * Adds a new archerTower onto the screen at position p.
	 */
	public void addTower(Tower t) {
		System.out.println("Tower added @"+t.getLocation().toString());
		towerList.add(t);
	}
	
	/**
	 * updates towers' targets and redraws them on the map
	 */
	public void updateAndReassignTowers() {
		for (Tower t : towerList) { 
			if(!enemyList.isEmpty()) {
				t.setEnemy(null);
				Enemy e = t.getPrioEnemy(enemyList);
				if(e != null && e.getDeathTicker() >= e.deathFrameCount()) {
					enemyList.remove(e);
					if(isRunning()) {
						e = enemyList.get(0);
					}
					else {
						endRound();
						return;
					}
				}
				if(e != null) {
					t.setEnemy(e);
					t.attack();
					e.setAttacked(true);
				}
			} 
			t.show(gc);
		}
	}

	/**
	 * Plays the timeline, and ultimately plays the game!
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
		return canvas;
	}

	@Override
	public GraphicsContext getGC() {
		return gc;
	}

	@Override
	public List<Enemy> getEnemyList() {
		return enemyList;
	}

	@Override
	public List<Tower> getTowerList() {
		return towerList;
	}

	@Override
	public Path getPath() {
		return path;
	}
	
	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean checkGameOver(Player p) {
		if (p.getHealth()<1) {
			timeline.stop();
			gc.drawImage(gameOver, 0, 0);
			return true;
		}
		return false;
	}
}
