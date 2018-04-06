package model;

import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy{

	private Image image; //new Image("file:..", false);
	private double health;
	private Point pos;
	private int bounty;
	private Path path;
	private GraphicsContext gc;
	private int sx, sy, sw, sh, dx, dy, dw, dh;
	
	public Enemy(Image image, double health, Path path, GraphicsContext gc) {
		this.image = image;
		this.health = health;
		this.path = path;
		this.gc = gc;
		sx = 0; 
		sy = 0;
		sw = 70;
		sh = 70;
		dx = 0;
		dy = 0;
		dw = 50; 
		dh = 50;
	}
	

	
	public void showEnemy() {
		 gc.drawImage(image, sx, sy, sw, sh, dx, dy, dw, dh);
	}
	
	
}
