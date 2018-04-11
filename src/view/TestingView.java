package view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
		
		map.spawnEnemies(1);
		
		
		this.setCenter(canvas);
		this.setOnMouseClicked(new MouseHandler());
	}
	public void show() {
		map.show();
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	private class MouseHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			System.out.println(event.getX()+"  "+event.getY());
			map.addTower(new Point((int)event.getX(), (int)event.getY()));
			
		}
		
	}

}
