package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Path;

public class Enemy {

	private Image image; //new Image("file:..", false);
	private double health;
	private Point pos;
	private int bounty;
	private Path path;
	private GraphicsContext gc;
	
	public Enemy(Image image, double health, Path path, GraphicsContext gc) {
		this.image = image;
		this.health = health;
		this.path = path;
		this.gc = gc;
	}
	
}
