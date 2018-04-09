package model;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import model.Path;

public class Map {
	private Path path = new Path();
	public boolean running = false;
	public boolean gameOver = false;
	
	
	
	public Map() {
		
	}
	
	public Path getPath() {
		System.out.println("Map: returned path");
		return this.path;
	}
}
