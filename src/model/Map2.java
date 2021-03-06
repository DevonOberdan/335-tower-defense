package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import model.enemy.Enemy;
import model.enemy.Ghost;
import model.enemy.Rider;
import model.enemy.Skeleton;
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
public class Map2 extends Map {
//	private List <Tower> availableTowers; //Available towers that we can select from the menu on the right.
	// ^^^^^^^ Needs to be implemented somehow. 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4481369109667527534L;
	private transient Timeline timeline; //The animator-2000.
	private Point start;
	private Player player;
	
	private transient GraphicsContext gc; //graphics context in which the canvas actually gets drawn.
	private List<Enemy> enemyList; //List of enemies
	private List<Tower> towerList; //List of towers
	private int maxWaveCount, waveCount;
	private boolean roundMode;
	private transient Image dragimg;
	private boolean dragging;
	private int dragx, dragy;
	private transient Image menu = new Image("file:images/menu.jpg");
	private transient Image background = new Image("file:images/maps/map2.png");

	
	/**
	 * Creates a testmap. This constructor will initialize each of our
	 * lists; enemies, towers, and creates the timeline for animating the
	 * background, and the enemies on it.
	 * 
	 * @param gc the graphics context in which we draw upon. THE EISEL FOR 
	 * ALL OF MY CREATIVITY AND FRUITINESS
	 */
	public Map2(Player p) { 
 		player = p;
 		roundMode = true;
		enemyList = new ArrayList<>();
		towerList = new ArrayList<>();
		timeline = new Timeline(new KeyFrame(Duration.millis(50),
				   new AnimateStarter2())); 
		 timeline.setCycleCount(Animation.INDEFINITE);
		 start = new Point(-30, 395);
		 this.maxWaveCount = 6;
		 this.waveCount = 0;
		 endZone = new Point (469, 469);
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
		for (int i=0; i<enemyCount+1; i++) {
			Enemy enemy = null; 
			Point offset = new Point(((i*75)), 0);
			Random r = new Random();
			int rand = r.nextInt(3);
			
			if (rand == 0 )
				enemy = new Ghost(new Map2_Path(), new Point((int) (start.getX() - offset.getX()), (int ) (start.getY() - offset.getY())));
			else if (rand == 1)
				enemy = new Rider(new Map2_Path(), new Point((int) (start.getX() - offset.getX()), (int ) (start.getY() - offset.getY())));
			else
				enemy = new Ghost(new Map2_Path(), new Point((int) (start.getX() - offset.getX()), (int ) (start.getY() - offset.getY())));
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
	private class AnimateStarter2 implements EventHandler<ActionEvent> {
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
				return;
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
						//destroyitall();
						e.setAttackPlayer();
						e.setDead();
					}
				}
				checkGameOver(player);
			}
			// why is this commented out???
			//enemyList.removeIf(e ->  (e.getLoc().getX()  )equals(new Point(357, 470 || (e.doWeRemove() && player.deposit(30, e)));
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
	/**
	 * Ends the round.
	 */
	public void endRound() {
		 Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Round Over");
		alert.setHeaderText(null);
		alert.setContentText("Round " + waveCount + " complete!");
		alert.show();
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
		System.out.println("Tower added @"+t.getLocation().toString());
		if (t.getBaseCost()<=player.getGold()) {
			player.withdraw(t.getBaseCost());
			player.addTower(t);
			t.setGC(gc);
		}
		else {
			System.out.println("You're broke");
		}	}
	
	/**
	 * updates towers' targets and redraws them on the map
	 */
	public void updateAndReassignTowers() {
		for (Tower t : player.getTowers()) { 
			if(!enemyList.isEmpty()) {
				t.setEnemy(null);
				switch(t.getTowerType()) {
				case area:
					List<Enemy> es = t.getPrioEnemies(enemyList);
					for(Enemy e : es) {
						if(e != null && e.getDeathTicker() >= e.deathFrameCount()) {
							enemyList.remove(e);
							if(isRunning()) {
								e = t.getPrioEnemy(enemyList);
							}
							else if(player.getHealth() >= 0 && enemyList.isEmpty()) {
								destroyitall();
								endMap();
								return;
							}
						}
						if(e != null) {
							t.setEnemy(e);
							e.setAttacked(true);
						}
					}
					break;
				case archer:
					Enemy e = t.getPrioEnemy(enemyList);
					if(e != null && e.getDeathTicker() >= e.deathFrameCount()) {
						enemyList.remove(e);
						if(isRunning()) {
							e = t.getPrioEnemy(enemyList);
						}
						else if(player.getHealth() >= 0 && enemyList.isEmpty()) {
							destroyitall();
							endMap();
							return;
						}
					}
					if(e != null) {
						t.setEnemy(e);
						e.setAttacked(true);
					}
					break;
				case catapult:
					List<Enemy> enList = t.getPrioEnemies(enemyList);
					for(Enemy en : enList) {
						if(en != null && en.getDeathTicker() >= en.deathFrameCount()) {
							enemyList.remove(en);
							if(isRunning()) {
								en = t.getPrioEnemy(enemyList);
							}
							else if(player.getHealth() >= 0 && enemyList.isEmpty()) {
								destroyitall();
								endMap();
								return;
							}
						}
						if(en != null) {
							t.setEnemy(en);
							en.setAttacked(true);
						}
					}
					break;
				default:
					break;
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
	
	@Override
	public void setGC(GraphicsContext gc)
	{
		this.gc = gc;
	}
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
		this.background = new Image("file:images/maps/map2.png");
	}
	
	@Override
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	@Override
	public int getMapID() {
		return 2;
	}
	
	@Override
	public void resetTimeline() {
		this.timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter2())); 
		 this.timeline.setCycleCount(Animation.INDEFINITE);
	}
	
	
	@Override
	public void decrementWave() {
		this.waveCount--;
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
	@Override
	public Path getPath () {
		return new Map2_Path();
	}
	
}
