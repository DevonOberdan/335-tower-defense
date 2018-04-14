package model;

import java.awt.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
	public boolean gameOver;
	public int enemyCount;
	
	
	private Canvas canvas;
	private GraphicsContext gc;	
	
	public Map(GraphicsContext gc) {
		gameOver = false;
		canvas = new Canvas (580,500);
		this.gc = gc;
	}
	
	public abstract int getEnemyCount();
	
	public abstract boolean isRunning();
	
	public abstract void spawnEnemies(int count);
	
	public Canvas getCanvas() { return canvas; }
	
	public GraphicsContext getGC() { return gc; }
	
	public abstract void addTower(Point p);
	
	public abstract void show();
	
	public abstract Path getPath();
}
