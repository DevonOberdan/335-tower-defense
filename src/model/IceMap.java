package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class IceMap extends Map {

	Image iceMap = new Image ("file:images/map_5.png");
	public IceMap(GraphicsContext gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void spawnEnemies(int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTower(Point p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Path getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
