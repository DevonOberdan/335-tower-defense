package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.util.Duration;
import model.enemy.ElfWizard;
import model.enemy.Enemy;
import model.enemy.TinyWizard;
import model.enemy.Troll;
import model.enemy.Wolf;
import model.tower.ETower;
import model.tower.Tower;
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
public class Map1 extends Map {
//	private List <Tower> availableTowers; //Available towers that we can select from the menu on the right.
	// ^^^^^^^ Needs to be implemented somehow. 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1301924038502607873L;
	private transient Timeline timeline; //The animator-2000.
	private Point start;
	private Player player;
//	private Canvas canvas; //The canvas upon which I lay all of my brilliant ideas upon
	private transient GraphicsContext gc; //graphics context in which the canvas actually gets drawn.
	private List<Enemy> enemyList; //List of enemies
	private List<Tower> towerList; //List of towers
	private Path path; //Path that the enemies must travel in.
	private int maxWaveCount, waveCount;
	//private Point endZone;
	private boolean roundMode;
	private transient Image dragimg;
	private boolean dragging;
	private int dragx, dragy;
	private transient Image menu = new Image("file:images/menu.jpg");
	private transient Image background = new Image("file:images/maps/map1.png");
	/**
	 * Creates a testmap. This constructor will initialize each of our
	 * lists; enemies, towers, and creates the timeline for animating the
	 * background, and the enemies on it.
	 *  
	 * @param gc the graphics context in which we draw upon. THE EISEL FOR 
	 * ALL OF MY CREATIVITY AND FRUITINESS
	 */
	public Map1(Player p) {
 		player = p;
 		//roundMode == true means between waves
 		roundMode = true;
		enemyList = new ArrayList<>();
		towerList = new ArrayList<>();
		timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter1())); 
		 timeline.setCycleCount(Animation.INDEFINITE);
		 start = new Point(-30, 40);
		 this.maxWaveCount = 6;
		 this.waveCount = 0;
		 this.endZone = new Point (490, 418);
		 this.dragging = false;
		 playSong();
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
		players.get(0).play();
		//int type1=(int) (Math.random()*enemyCount), type2=0, type3;
		for (int i=0; i<enemyCount+1; i++) {
			Enemy enemy = null; 
			Point offset = new Point(((i*75)), 0);
			if (enemyCount == 0 || enemyCount == 1)
				enemy = new ElfWizard(new Map1_Path(), new Point((int) (start.getX() - offset.getX()), (int ) (start.getY() - offset.getY())));
			else if (enemyCount == 2 || enemyCount == 3)
				enemy = new Troll(new Map1_Path(), new Point((int) (start.getX() - offset.getX()), (int ) (start.getY() - offset.getY())));
			else
				enemy = new Wolf(new Map1_Path(), new Point((int) (start.getX() - offset.getX()), (int ) (start.getY() - offset.getY())));
			enemyList.add(enemy);
		}
	}
	
	/*
	 * Private handler for timeline that will target an enemy for each tower, and
	 * animate each object that we have placed on the map. THis is where
	 * all of the animating, targeting, and logic of object interactions takes place.
	 * 
	 * Will likely need some sort of refactoring and thought to make it more
	 * OOP-y, but this works.
	 *
	 */
	private class AnimateStarter1 implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			gc.clearRect(0, 0, 580, 500);
			gc.drawImage(menu, 0, 0);
			gc.drawImage(background, 0, 0);
			player.draw(); 
			if(enemyList.isEmpty() && waveCount < maxWaveCount && player.getHealth() >= 0 && !roundMode) {
				toggleRound();
				endRound();
			} else if(mapFinished()){
				endMap();
			}
			
			updateAndReassignTowers();
			
			if(dragging) {
				gc.drawImage(dragimg, dragx-30, dragy-70, 60, 80);
			}
			
			if(roundMode) {
				for(Tower t : player.getTowers()) {
					t.endTimers();
				}
				return;
			} else {
				for(Tower t : player.getTowers()) {
					if(!t.isAnimating())
						t.startTimers();
				}
			}
			
			for (Enemy e : enemyList) {
				e.setEnList((ArrayList<Enemy>) enemyList);
				if(e.getDeathTicker() >= e.deathFrameCount()) {
					e = enemyList.get(0);
				}
				if(e != null) {
					
					e.show(gc);
					e.setAttacked(false);
					if (!e.getDead() && e.attackPlayer(player, endZone)) {
						e.setAttackPlayer();
						e.setDead();
						e.setHel(0);
					}
				}
				checkGameOver(player);
			}
			enemyList.removeIf(e -> e.getLoc().equals(new Point(495, 410)) || (e.doWeRemove() && player.deposit(200, e)));
		}
	}
	
	/**
	 * Ends the round.
	 */
	public void endMap() { 
		players.get(0).stop();
		timeline.stop();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Map Over");
		alert.setHeaderText(null);
		alert.setContentText("You've defeated the Legion! :-)\nClick OK, then click the screen to advance to the\nnext stage of the game.");
		alert.show();
	}

	@Override
	public void toggleRound() {
		this.roundMode = !this.roundMode;
	}
	
	@Override
	public void setGC(GraphicsContext gc)
	{
		this.gc = gc;
	}
	
	/**
	 * Ends the round.
	 */
	public void endRound() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Round Over");
		alert.setHeaderText(null);
		alert.setContentText("Round " + waveCount + " complete!");
		alert.show();
		//this.playVectorySong("L.mp3");
	}
	
	
	/**
	 * Pauses the game.
	 */
	@Override
	public void pause() {
		this.timeline.pause();
	}
	/**
	 * Resumes the game.
	 */
	@Override
	public void play() {
		this.timeline.play();
	}
	
	/**
	 * Adds a new archerTower onto the screen at position p.
	 */
	public void addTower(Tower t) {
		if (t.getBaseCost()<=player.getGold()) {
			player.withdraw(t.getBaseCost());
			player.addTower(t);
			t.setGC(gc); 
		}
		else {
			System.out.println("You're broke");
		}	
	} 
	
	/**
	 * updates towers' targets and redraws them on the map
	 */
	public void updateAndReassignTowers() {
		for (Tower t : player.getTowers()) { 
			t.giveRoundMode(this.getRoundMode());
			if(!enemyList.isEmpty()) {
				t.setEnemy(null);
				if(t.getTowerType() == ETower.area || t.getTowerType() == ETower.catapult) {
					List<Enemy> es = t.getPrioEnemies(enemyList);
					for(Enemy e : es) {
						if(e != null && e.getDeathTicker() >= e.deathFrameCount()) {
							enemyList.remove(e);
							if(isRunning()) {
								e = t.getPrioEnemy(enemyList);
							}
							else if(mapFinished()) {
								endMap();
								destroyitall();
								return;
							}
						}
						if(e != null) {
							t.setEnemy(e);
							e.setAttacked(true);
						}
					}
				}
				else {
					Enemy e = t.getPrioEnemy(enemyList);
					if(e != null && e.getDeathTicker() >= e.deathFrameCount()) {
						enemyList.remove(e);
						if(isRunning()) {
							e = t.getPrioEnemy(enemyList);
						}
						else if(player.getHealth() >= 0 && enemyList.isEmpty()) {
							endMap();
							return;
						}
					}
					if(e != null) {
						t.setEnemy(e);
						e.setAttacked(true);
					}
				}
			} 
			t.show(gc);
		}
	}
	/**********************************************************************/
	/**
	 * Plays the timeline, and ultimately plays the game!
	 */
	public void show() {
		timeline.play();
	}
	
	/**
	 * Gets the current path for this map for the enemies to follow.
	 */
	/**
	 * Gets the number of enemies left to be killed.
	 */
	@Override
	public int getEnemyCount() {
		return enemyList.size();
	}
	
	/**
	 * Returns true if the player has finished all enemies on the map.
	 */
	@Override
	public boolean mapFinished() {
		return this.enemyList.isEmpty() && this.waveCount >= this.maxWaveCount && player.getHealth() >= 0;
	}
	/**
	 * Returns true if there exists an enemy count
	 * in our enemy list; being that there are still enemies
	 * to be killed!
	 */
	@Override
	public boolean isRunning() {
		return getEnemyCount() > 0 && 
				(timeline.getStatus() == Animation.Status.RUNNING || timeline.getStatus() == Animation.Status.PAUSED) &&
				this.getWaveCount() < this.getMaxWaveCount();
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
	public boolean checkGameOver(Player p) {
		if (p.getHealth()<1) {
			timeline.stop();
			gc.drawImage(new Image("file:images/game_over.png"), 0, 0);
			return true;
		}
		return false;
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public void incrementWave() {
		this.waveCount++;	
	}

	@Override
	public int getMaxWaveCount() {
		return this.maxWaveCount;
	}
	
	@Override
	public int getWaveCount() {
		return this.waveCount;
	}

	@Override
	public boolean getRoundMode() {
		return this.roundMode;	
	}
	
	@Override
	public Timeline getTimeline() {
		return this.timeline;
	}
	
	@Override
	public void destroyitall() {
		this.endZone = null;
		this.enemyList.clear();
		this.enemyList = null;
		this.gc = null;
		this.player = null;
		this.start = null;
		this.timeline.stop();
		this.timeline = null;
		this.towerList.clear();
		for(Tower tow: player.getTowers()) { tow.endTimers(); }
		this.getPlayer().getTowers().clear();
		this.towerList = null;
	}
	
	@Override
	public void resetMenu() {
		this.menu = new Image("file:images/menu.jpg");
	}
	
	@Override
	public void resetBackground() {
		this.background = new Image("file:images/maps/map1.png");
	}
	
	@Override
	public void decrementWave() {
		this.waveCount--;
	}
	
	@Override
	public void setPlayer(Player p) {
		this.player = p;
	}
	@Override
	public int getMapID() {
		return 1;
	}
	
	@Override
	public void resetTimeline() {
		this.timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter1())); 
		 this.timeline.setCycleCount(Animation.INDEFINITE);
	}
	
	@Override
	public void setRoundMode(boolean bool) {
		this.roundMode = bool;
	}
	
	@Override
	public void setDragged(Image img, boolean bool, int x, int y) {
		this.dragx = x;
		this.dragy = y;
		this.dragging = bool;
		this.dragimg = img;
	}
}