package view;

import java.util.Observable;
import java.util.Observer;

import controller.TowerDefenseGUI;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class IntroView extends StackPane implements Observer{

	private Canvas canvas;
	private GraphicsContext gc;
	private Timeline timeline;
	private TowerDefenseGUI gui;
	
	public IntroView(TowerDefenseGUI gui) {
		this.gui = gui;
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D();
		this.getChildren().add(canvas);
		 timeline = new Timeline(new KeyFrame(Duration.millis(33), new AnimateStarter()));
		 timeline.setCycleCount(Animation.INDEFINITE);
	}
	public void play() {
		timeline.play();
	}
	public void done() {
		gui.next();
	}
	
	  private class AnimateStarter implements EventHandler<ActionEvent> {
		    int y;

		    public AnimateStarter() {
		    	y=0;

		    }

		    @Override
		    public void handle(ActionEvent event) {
		    
		    	String str = "";
		    	if (y<10)
		    		str = "0";
		    	str+=y;
			gc.clearRect(0, 0, 580, 500);
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, 580, 500);
			gc.drawImage(new Image("file:images/intro/intro"+str+".png"), 0, 0);
			y++;

			if (y>89) {
				timeline.stop();
				done();
			}
		  }
	  }
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
