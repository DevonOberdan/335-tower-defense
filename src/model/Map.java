package model;

import java.awt.Point;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Path;

public abstract class Map {
	public boolean running;
	public boolean gameOver;
	
	protected Image background;
	protected Image enemy;
	protected Image menuBar;
	
	protected Canvas canvas;
	protected GraphicsContext gc;	
	protected List<Enemy> enemyList;
	protected List<Tower> towerList;
	protected Path path;
	private String mapName;
	
	public Map(String name) {
		running = true;
		gameOver = false;
		canvas = new Canvas (580,500);
		mapName = name;
		path = new Path(mapName);
	}
	
	public abstract void spawnEnemies(int count);
	
	public Canvas getCanvas() { return canvas; }
	
	public GraphicsContext getGC() { return gc; }
	
	public List<Enemy> getEnemyList() { return enemyList; }
	
	public List<Tower> getTowerList() { return towerList; }
	
	public Path getPath() { return path; }
	
	public abstract void addTower(Point p);
	
	public abstract void show();
	
}
