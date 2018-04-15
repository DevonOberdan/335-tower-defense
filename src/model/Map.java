package model;

import java.awt.Point;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Path;


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
public abstract class Map {
	public boolean gameOver; //game is done
	public int enemyCount; //number of enemies still alive
	
	protected Image background; //background of the map
	protected Image menuBar; //Menu bar; where we select different enemies.
	
	protected Canvas canvas; //The canvas upon which I lay all of my brilliant ideas upon
	protected GraphicsContext gc; //graphics context in which the canvas actually gets drawn.
	protected List<Enemy> enemyList; //List of enemies
	protected List<Tower> towerList; //List of towers
	protected Path path; //Path that the enemies must travel in.	
	
	/**
	 * Constructor for this Map class that doesn't do anything fancy,
	 * just initializes our variables.
	 * 
	 * What is mapName and why is it here
	 * @param name
	 */
	public Map() {
		gameOver = false;
		canvas = new Canvas (580,500);
	}
	
	/**
	 * @return Gets the number of enemies left in the game
	 */
	public abstract int getEnemyCount();
	
	/**
	 * @return Is the game still running or not
	 */
	public abstract boolean isRunning();
	
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
	 * to reach teh goal.
	 * @return
	 */
	public abstract Path getPath();
	
	/**
	 * Adds a tower to this map at location p.
	 * @param p
	 */
	public abstract void addTower(Point p);
	
	/**
	 * Shows this map.
	 */
	public abstract void show();
	
}
