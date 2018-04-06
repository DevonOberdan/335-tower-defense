package view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import model.Map;

public class GameView extends BorderPane implements Observer{
 /*
  * Default GameUI on some side of the screen,
  * Game view / Map occupying everywhere else.
  */
	private Map theGame;
	private final Image background = new Image("file:images/map_1.jpg");
	private Canvas canvas;
	private GraphicsContext gc;
	
	final int canvasL = 900; // canvasL will be the size of the canvas/gc
	
	public GameView (Map map) {
		
		theGame = map; // copy the input game from the input to theGame
		canvas = new Canvas (canvasL, canvasL); // Initialize the canvas with canvasLxcanvasL
		gc = canvas.getGraphicsContext2D(); 
		
		drawMap();
		this.setCenter(canvas); // set the center of this boarderpane to be the canvas
	}

	private void drawMap() {
		gc.drawImage(background, 0, 0);
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	
}
