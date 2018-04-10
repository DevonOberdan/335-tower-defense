package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class NewEnemy {

	private Point loc;
	private int hel;
	private final Image img;
	private int speed;

	public NewEnemy(Point offset, int speed) {
		loc = new Point(-60 -(int) offset.getX(), 17 -(int) offset.getY());
		hel = 100;
		this.speed = speed;
		img = new Image("file:images/enemy_sprite.png");
	}
	
	public void setLoc(Point p) { loc = p; }
	public void setHel(int h)   { hel = h; }
	
	public Point getLoc()   { return loc; }
	public int   getHel()   { return hel; }
	
	public void show(GraphicsContext gc, int num) {
		switch (num) {
		case 0:
			gc.drawImage(img, 0, 0, 60, 60, loc.getX(), loc.getY(), 60, 60);
			break;
		case 1:
			gc.drawImage(img, 60, 0, 60, 60, loc.getX(), loc.getY(), 60, 60);
			break;
		case 2:
			gc.drawImage(img, 120, 0, 60, 60, loc.getX(), loc.getY(), 60, 60);
			break;
		default:
				gc.drawImage(img, 180, 0, 60, 60, loc.getX(), loc.getY(), 60, 60);
				break;
		}
		move();
	}
	private void move() {
		double x = loc.getX();
		double y = loc.getY();
		loc.setLocation(x+speed, y);
	}
}
