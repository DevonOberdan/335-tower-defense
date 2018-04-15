package model;

import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TestEnemy extends Enemy {

	private Point loc;
	private int hel;
	private final Image img_n, img_d;
	private Image img;
	private int speed;
	private Path path;
	private Point turns;
	private boolean attacked;
	
	private final Image testing = new Image("file:images/testing.png") ;

	public TestEnemy(int speed, Path path, Point start) {
		super(speed, speed, path, start);
		loc =  start;
		
		this.path = super.getPath();
		img_n = new Image("file:images/enemy_sprite.png");
		img_d = new Image("file:images/enemy_sprite_damge.png");
		this.hel = super.getHel();
		this.attacked = false;
		this.speed = super.getSpeed();
	}
 
	public void show(GraphicsContext gc, int num) {
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
		//System.out.println("Old location: " + loc.toString());
		move();
		//System.out.println("New locations: " + loc.toString());
	}
	
	@Override
	public void setAttacked(boolean v) {
		attacked = v;
	}
	
	public void move() {
		this.loc = (new Point((int)(loc.getX() + speed*turns.getX()),
				(int)(loc.getY() + speed*turns.getY())));
	}
	public void checkTurns() {
		turns = path.checkTurns(loc);
	}
	
}
