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
	private final Image enemy = new Image("file:images/enemy.png");
	private final Image menuBar = new Image("file:images/menu.jpg");
	private final Image tower1 = new Image("file:images/tower.png");
	private Canvas canvas;
	private GraphicsContext gc;
	
	final int canvasL = 900; // canvasL will be the size of the canvas/gc
	
	public GameView (Map map) {
		
		theGame = map; // copy the input game from the input to theGame
		canvas = new Canvas (canvasL, canvasL); // Initialize the canvas with canvasLxcanvasL
		gc = canvas.getGraphicsContext2D(); 
		drawMenuBar();
		drawMap();
		drawEnemy();
		drawTower();
		this.setCenter(canvas); // set the center of this boarderpane to be the canvas
		
	}

	private void drawEnemy()
	{
		gc.drawImage(enemy, -8, 38);
	}
	private void drawTower()
	{
		gc.drawImage(tower1, 80, -270);
	}
	private void drawMenuBar() {
		gc.drawImage(menuBar, 0, 0);
	}
	private void drawMap() {
		gc.drawImage(background, 0, 0);
		
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	
}
