package view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import Ice.IceMap;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
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
		this.setOnMouseClicked(e ->{
			System.out.println(e.getX()+"  "+e.getY());
			map.addTower(new Point((int)e.getX(), (int)e.getY()));
		});
		this.setOnMouseMoved(e -> {
			if(this.map.gameOver) {
				this.setCenter((Node)(Observer) new WelcomeView());
				this.map.gameOver = false;
				this.setOnMouseMoved(null);
			}
		});
	}
	public void show() {
		map.show();
	}
	@Override
	public void update(Observable o, Object arg) {
		
	}


}
