package model;

import java.awt.Point;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class NewEnemy {

	private Point loc;
	private int hel;
	private final Image img_n, img_d;
	private Image img;
	private int speed;
	private Path path;
	private Point turns;
	private boolean attacked;
	
	private final Image testing = new Image("file:images/testing.png") ;

	public NewEnemy(Point offset, int speed, Path path) {
		loc = new Point(-30 -(int) offset.getX(), 47 -(int) offset.getY());
		hel = 100;
		this.speed = speed;
		this.path = path;
		turns = new Point(1,1);
		img_n = new Image("file:images/enemy_sprite.png");
		img_d = new Image("file:images/enemy_sprite_damge.png");
		attacked = false;
	}
	
	public void setLoc(Point p) { loc = p; }
	public void setHel(int h)   { hel = h; }
	
	public Point getLoc()   { return loc; }
	public int   getHel()   { return hel; }
	
	public void show(GraphicsContext gc, int num) {
		if (hel<1)
			return;
		if (attacked)
			img = img_d;
		else
			img = img_n;
		
		switch (num) {
		
		case 0:
			gc.drawImage(img, 0, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
			break;
		case 1:
			gc.drawImage(img, 60, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
			break;
		case 2:
			gc.drawImage(img, 120, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
			break;
		default:
				gc.drawImage(img, 180, 0, 60, 60, loc.getX()-30, loc.getY()-30, 60, 60);
				break;
		}
		gc.drawImage(testing, loc.getX(), loc.getY());
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
	public void setAttacked(Boolean v) {
		attacked = v;
	}
	public Tower checkTower(List<Tower> towers) {
		for (Tower t : towers) {
			attacked = false;
			if (withenRange(t) && t.getCurrentEnemy()==null) {
			 	attacked = true;
				t.setEnemy(this);
				return t;
			}
			else if(withenRange(t) && t.getCurrentEnemy()!=null && t.getCurrentEnemy().equals(this))
				attacked=true;
			t.attack();
		}
		
		return null;
	}
	public boolean withenRange(Tower t) {
		int dist = (int) Math.sqrt(Math.pow(loc.getX() - t.getLocation().getX(), 2) +
				Math.pow((loc.getY() - t.getLocation().getY()), 2));
		if (dist <= t.getRange())
			return true;
		
		return false;
	} 
	
}
