package view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import model.IceMap;
import model.Map;
import model.TestMap;

public class GameView extends BorderPane implements Observer{

	private Map map;
	private Canvas canvas;
	private GraphicsContext gc;
	private int ptr;
	public GameView() {
		BorderPane pane = new BorderPane();
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D();
		this.map = new TestMap(gc);
		this.map.spawnEnemies(5);
		this.ptr = 0;

		//showIntroCutscene();
		
		pane.setCenter(canvas);
		this.setCenter(pane);
		this.setOnMouseClicked(e ->{

			switch(ptr) {
				case 0:
					if(this.map != null) {
						if(!this.map.isRunning()) {
							//showFirstCutscene();
							this.map = new IceMap(gc);
							this.map.spawnEnemies(1);
							this.map.show();
							ptr++;
						}
					}
					
					break;
					
				case 1:
					if(this.map != null) {
						if(!this.map.isRunning()) {
							//showSecondCutscene();
							this.map = new TestMap(gc);
							this.map.spawnEnemies(1);
							this.map.show();
							ptr++;
						}
					}
					
					break;
					
				case 2:
					if(this.map != null) {
						if(!this.map.isRunning()) {
							//showOutroCutscene();
							System.out.println("Entered outro-- click again to get back to main menu");
							ptr++;
						}
					}
					
					break;
					
				default:
					if(!this.map.isRunning()) {
						this.setCenter(null);
						this.setOnMouseClicked(null);
						this.map = null;
						this.canvas = null;
						this.gc = null;
						this.setCenter((Node)(Observer) new WelcomeView());
					}
					break;
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
