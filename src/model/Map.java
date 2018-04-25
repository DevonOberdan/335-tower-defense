package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import model.Path;
import model.enemy.Enemy;
import model.tower.Tower;



/**
 * Abstract class Map includes a variety of functions that will allow
 * us to easily implement another Map, with varying backgrounds and paths
 * for the enemies to follow. This abstract class has functions for getting
 * the canvas to draw on, showing the map, getting the map's path, 
 * and spawning enemies onto the map. spawnEnemies is included
 * in the abstract class, as each map might have a different wave structure.
 * @author Taite Nazifi
 *
 */
public abstract class Map extends StackPane{
	// the background image
	protected final Image gameOver = new Image("file:images/game_over.png");
	
	protected ArrayList<Enemy> enemyList; //List of enemies
	protected List<Tower> towerList; //List of towers
	// the end-zone where the enemies are headed
	protected Point endZone;
	/**
	 * @return Gets the number of enemies left in the game
	 */
	public abstract int getEnemyCount();
	/**
	 * returns the timeline
	 */
	public abstract Timeline getTimeline();
	/**
	 * @return Is the game still running or not
	 */
	public abstract boolean isRunning();
	/**
	 * Gets the count of waves left.
	 * @return 
	 */
	public abstract int getWaveCount();
	/**
	 * Places a certain number of enemies. Slightly complex, and waves
	 * are introduced in this function. It is easier code, and stuff. May
	 * not be OOP-y but heck, who is
	 * @param count How many enemies to spawn onto the map.
	 */
	public abstract void spawnEnemies(int count);
	
	/**
	 * @return canvas object
	 */
	public abstract Canvas getCanvas();
	
	/**
	 * 
	 * @return GraphicContext object pulled from the canvas.
	 */
	public abstract GraphicsContext getGC();
	
	/**
	 * Returns the enemy list. 
	 */
	public abstract List<Enemy> getEnemyList();
	
	/**
	 * Returns the tower list.
	 * @return
	 */
	public abstract List<Tower> getTowerList();
	
	/**
	 * REturns the path of this map that the enemies have to travel in order
	 * to reach the goal.
	 * @return
	 */
	public abstract Path getPath();
	
	/**
	 * increments the wave count
	 */
	public abstract void incrementWave();
	
	/**
	 * Sets map to round mode
	 */
	public abstract void toggleRound();
	/**
	 * Adds a tower to this map at location p.
	 * @param p
	 */
	public abstract void addTower(Tower t);
	
	/**
	 * Sets the graphics context
	 */
	public abstract void setGC(GraphicsContext gc);
	/**
	 * Shows this map.
	 */
	public abstract void show();
	
	/**
	 * Gets the player object for this map.
	 * @return 
	 */
	public abstract Player getPlayer();
	
	/**
	 * checks if the player has died and makes the game end
	 */
	public abstract boolean checkGameOver(Player p);

	/**
	 * @return returns the maximum amount of waves for this map.
	 */
	public abstract int getMaxWaveCount();
	
	/**
	 * Returns the boolean round mode value.
	 */
	public abstract boolean getRoundMode();

	/**
	 * returns true if the game has ended.
	 * @return
	 */
	public abstract boolean mapFinished();
	
	/**
	 * destroys everything
	 */
	public abstract void destroyitall();
	
	/**
	 * Sets the img that we need to draw to be dragged to this
	 */
	public abstract void setDragged(Image img, boolean boo, int x, int y);
	
	
	public void giveList() {
		for(Enemy en : enemyList) {
			en.setList(enemyList);
		}
	}
}
