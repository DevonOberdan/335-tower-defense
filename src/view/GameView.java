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
			this.map.spawnEnemies(1);
			break;
		case "Ice Map":
			this.map = new IceMap(gc);
			this.map.spawnEnemies(1);
			break;
		default:
			this.map = new TestMap(gc);
			break;
		}
		
		pane.setCenter(canvas);
		this.setCenter(pane);
		this.setOnMouseClicked(e ->{
			if(this.map.gameOver) {
				this.setCenter((Node)(Observer) new WelcomeView());
				this.map.gameOver = false;
			}
			if(map != null) {
				System.out.println(e.getX()+"  "+e.getY());
				map.addTower(new Point((int)e.getX(), (int)e.getY()));
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
