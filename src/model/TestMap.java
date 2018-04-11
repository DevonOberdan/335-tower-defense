package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class TestMap extends Map {
	private Path path;
	private List <NewEnemy> enemyList;
	private List <Tower> towerList;
	private List <Tower> availableTowers;
	
	private final Image background = new Image("file:images/map_1.jpg");
	private final Image menuBar = new Image("file:images/menu.jpg");
	
	private Canvas canvas;
	private GraphicsContext gc;
	private Timeline timeline;
	private Tower tower;
	
	public TestMap(GraphicsContext gc) {
		super(gc);
		this.gc = super.getGC();
		path = new Path();
		enemyList = new ArrayList<>();
		towerList = new ArrayList<>();
		timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter())); 
		 timeline.setCycleCount(Animation.INDEFINITE);
		 tower = new ArcherTower(null);
	}
	public void spawnEnemies(int enemyCount) {
		for (int i=0; i<enemyCount; i++) {
			enemyList.add(i, new NewEnemy(new Point(i*50, 0), 2, path));
		}
		System.out.printf("%d enemies have been spawned.\n", enemyCount);
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
			for (Tower t : towerList) {
				NewEnemy e = t.getPrioEnemy(enemyList);
				t.setEnemy(e);
				t.attack();
				t.show(gc);
			}
			for (NewEnemy e : enemyList) {
				e.show(gc, img);
				e.setAttacked(false);
			}
			img++;
			tic++;
		}
		
	}
	
	public void addTower(Point p) {
		System.out.println("Tower added @"+p);
		towerList.add(new ArcherTower(p));
	}
	
	
	public void show() {
		timeline.play();
	}
	
	
	public Path getPath() {
		System.out.println("Map: returned path");
		return this.path;
	}
}
