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
import model.IceMap;
import model.Map;
import model.TestMap;

public class GameView extends BorderPane implements Observer{

	private Map map;
	private Canvas canvas;
	private GraphicsContext gc;
	public GameView(String mapName) {
		BorderPane pane = new BorderPane();
		
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D(); 
		switch (mapName) {
		case "Testing Map":
			this.map = new TestMap(gc);
			this.map.spawnEnemies(10);
			break;
		case "Ice Map":
			this.map = new IceMap(gc);
			this.map.spawnEnemies(1);
			break;
			default:
				this.map = new TestMap(gc);
		}
		
		
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
