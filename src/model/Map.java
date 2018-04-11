package model;

import java.awt.Point;
import java.util.ArrayList;
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
import model.Path;

public abstract class Map {
	private Path path;
	private List <NewEnemy> enemyList;
	private List <Tower> towerList;
	private List <Tower> availableTowers;
	public boolean running;
	public boolean gameOver;
	
	private final Image background = new Image("file:images/map_1.jpg");
	private final Image menuBar = new Image("file:images/menu.jpg");
	
	private Canvas canvas;
	private GraphicsContext gc;
	private Timeline timeline;
	private Tower tower;
	
	public Canvas getCanvas() { return canvas; }
	public GraphicsContext getGC() { return gc; }
	
	
	
	public Map(GraphicsContext gc) {
		running = true;
		gameOver = false;
		canvas = new Canvas (580,500);
		this.gc = gc;
	}
		
	public abstract void addTower(Point p);
	
	public abstract void show();
	
	
	public abstract Path getPath();
}
