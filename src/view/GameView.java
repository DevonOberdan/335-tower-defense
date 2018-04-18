package view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import model.IceMap;
import model.Map;
import model.Player;
import model.TestMap;
import model.Tower;

public class GameView extends BorderPane implements Observer{

	private Map map;
	private Canvas canvas;
	private GraphicsContext gc;
	private int ptr;
	private Player player;
	public GameView() {
		BorderPane pane = new BorderPane();
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D();
		player = new Player(this.gc, 100, 500);
		this.map = new TestMap(player, gc);
		this.map.spawnEnemies(5);
		this.ptr = 0;
		/*
		 * Where we define what happens when we click a tower and drag it. This
		 * is going to be really disgusting and messy. I hate this. 
		 */
		this.setOnMouseDragged(e -> {
			
		});
		
		/*
		 * Makes it easier to transition. 
		 */

		//showIntroCutscene();
		
		pane.setCenter(canvas);
		this.setCenter(pane);
		
		this.setOnMouseClicked(e ->{
			if(this.player.isDead()) {
				this.setCenter(null);
				this.setOnMouseClicked(null);
				this.map = null;
				this.canvas = null;
				this.gc = null;
				this.setCenter((Node)(Observer) new WelcomeView());
				return;
			}
			
			if(this.map != null && !this.map.isRunning()) {
				switch(ptr) {
				case 0:
					if(this.map != null) {
						if(!this.map.isRunning()) {
							//showFirstCutscene();
							this.map = new IceMap(player, gc);
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
							this.map = new TestMap(player, gc);
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
			}
			
			if(map != null && map.isRunning()) {
				if(e.getButton() == MouseButton.PRIMARY) {
					int x = (int) e.getX();
					int y = (int) e.getY();
					for(Tower t : player.getTowers()) {
						if(x < t.getLocation().getX()-20 || x > t.getLocation().getX()+20
								|| y < t.getLocation().getY()-10 || y > t.getLocation().getY()+10) {
							
							t.setSelected(false);
							
						} else { //must be in bounds
							t.setSelected(true);
						}
					}
				} else if(e.getButton() == MouseButton.SECONDARY) {
					System.out.println(e.getX()+"  "+e.getY());
					((TestMap) map).addMultiTower(new Point((int)e.getX(), (int)e.getY()));
				}
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
