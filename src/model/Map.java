package model;

import java.awt.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.Path;

public abstract class Map {
	public boolean running;
	public boolean gameOver;
	
	
	private Canvas canvas;
	private GraphicsContext gc;	
	
	public Map(GraphicsContext gc) {
		running = true;
		gameOver = false;
		canvas = new Canvas (580,500);
		this.gc = gc;
	}
	
	public abstract void spawnEnemies(int count);
	
	public Canvas getCanvas() { return canvas; }
	
	public GraphicsContext getGC() { return gc; }
	
	public abstract void addTower(Point p);
	
	public abstract void show();
	
	public abstract Path getPath();
}
