package model;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import model.Path;

public class Map {
	private Path path;
	
	
	
	public Map() {
		
	}
	
	public Path getPath() {
		System.out.println("Map: returned path");
		return this.path;
	}
}
