package model;

import java.awt.Point;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class NewEnemy {

	private Point loc;
	private int hel;
	private final Image img;
	private int speed;
	private Path path;
	private Point turns;

	public NewEnemy(Point offset, int speed, Path path) {
		loc = new Point(-60 -(int) offset.getX(), 17 -(int) offset.getY());
		hel = 100;
		this.speed = speed;
		this.path = path;
		turns = new Point(1,1);
		img = new Image("file:images/enemy_sprite.png");
	}
	
	public void setLoc(Point p) { loc = p; }
	public void setHel(int h)   { hel = h; }
	
	public Point getLoc()   { return loc; }
	public int   getHel()   { return hel; }
	
	public void show(GraphicsContext gc, int num) {
		if (hel<1)
			return;
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
		checkTurns();
		move();
	}
	private void move() {
		loc.setLocation(loc.getX() + speed*turns.getX(),
				loc.getY() + speed*turns.getY());
	}
	private void checkTurns() {
		turns = path.checkTurns(loc);
	}
}
