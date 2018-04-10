package view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import model.Map;

public class TestingView extends BorderPane implements Observer{

	private Map map;
	private Canvas canvas;
	private GraphicsContext gc;
	private final Image background = new Image("file:images/map_1.jpg");
	public TestingView() {
		
		
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D(); 
		map = new Map(gc);
		//gc.drawImage(background, 100, 100);
		
		map.spawnEnemies(10);
		
		
		this.setCenter(canvas);
	}
	public void show() {
		map.show();
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
