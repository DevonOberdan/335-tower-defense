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
import model.TestMap;

public class TestingView extends BorderPane implements Observer{

	private TestMap map;
	private Canvas canvas;
	private GraphicsContext gc;
	private final Image background = new Image("file:images/map_1.jpg");
	public TestingView() {
		BorderPane pane = new BorderPane();
		
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D(); 
		map = new TestMap(gc);
		//gc.drawImage(background, 100, 100);
		
		map.spawnEnemies(10);
		
		pane.setCenter(canvas);
		this.setCenter(pane);
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
