package model;

import java.awt.Point;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Enemy{

	private Image enemy; //new Image("file:..", false);
	private Image background;
	private double health;
	private Point pos;
	private int bounty;
	private Path path;
	private GraphicsContext gc;
	public int sx, sy, sw, sh, dx, dy, dw, dh;
	 private Timeline timeline;
	 private double xOffset = 0;
	 private double yOffset = 0;
	 
	 private double speed = 2;
	
	public Enemy(Image enemy, Image background, double health, Path path, GraphicsContext gc) {
		this.enemy = enemy;
		this.background = background;
		this.health = health;
		this.path = path;
		this.gc = gc;
		sx = 0; 
		sy = 0;
		sw = 60;
		sh = 60;
		dx = -60;
		dy = 17;
		dw = 60; 
		dh = 60;
		
		timeline = new Timeline(new KeyFrame(Duration.millis(100),
                new AnimateStarter())); 
		 timeline.setCycleCount(Animation.INDEFINITE);
		 
		 
	}
	private class AnimateStarter implements EventHandler<ActionEvent> {
	    private int tic = 0;
	   // private double x = -100;
	   // private double y = 17;
	  
	    public AnimateStarter() {
	      // TODO A3: Draw both images. Which one first, ship or background?
	    	 gc.drawImage(background, 0, 0);
	    	gc.drawImage(enemy, sx, sy, sw, sh, dx, dy, dw, dh);
	    }
	    
	    @Override
	    // TODO A4: This handle method is called every 100ms to draw a ship in a new location
	    public void handle(ActionEvent event) {
	      gc.drawImage(background, 0, 0);
	      if (dx<200 && dy<20) {
	    	  	xOffset = speed;
	    	  	yOffset = 0;
	      }
	      else if (dx>=200 && dy<400){
	    	  	xOffset = 0;
	    	  	yOffset = speed;
	      }
	      else {
	    	  xOffset = speed;
	    	  yOffset = 0;
	      }
	      dx += xOffset;
	      dy += yOffset;
	      //y += 0.08;
	      if (tic%4 == 0)
	    	  	sx=0;
	      else 
	    	  	sx+=60;
	      gc.drawImage(enemy, sx, sy, sw, sh, dx, dy, dw, dh);
	      tic++;
	      if (dx>440) {
	    	  	timeline.stop();
	    	  	//theGame.gameOver = true;
	      }
	    }
	  }
	

	
	public void showEnemy() {
		
		 timeline.play();
		 System.out.println(path.turns.get(0));
	}
	
	
}
