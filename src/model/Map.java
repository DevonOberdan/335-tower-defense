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

public class Map {
	private Path path;
	private List <NewEnemy> enemyList;
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
		path = new Path();
		enemyList = new ArrayList<>();
		running = true;
		gameOver = false;
		canvas = new Canvas (580,500);
		this.gc = gc;
		tower = new ArcherTower();
		timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter())); 
		 timeline.setCycleCount(Animation.INDEFINITE);
	}
	public void spawnEnemies(int enemyCount) {
		for (int i=0; i<enemyCount; i++) {
			enemyList.add(i, new NewEnemy(new Point(i*50, 0), 2));
		}
		System.out.printf("%d enemies have been spwaned.\n", enemyCount);
	}
	
	private class AnimateStarter implements EventHandler<ActionEvent> {
		private int tic=0, img=0;
		@Override
		public void handle(ActionEvent event) {
			gc.clearRect(0, 0, 580, 500);
			gc.drawImage(menuBar, 0, 0);
			gc.drawImage(background, 0, 0);
			
			if (img == 4)
				img=0;
			for (NewEnemy e : enemyList) {
				e.show(gc, img);
			}
			img++;
			tic++;
		}
		
	}
	
	
	
	public void show() {
		timeline.play();
	}
	
	
	public Path getPath() {
		System.out.println("Map: returned path");
		return this.path;
	}
}
