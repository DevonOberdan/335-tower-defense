package view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import model.ArcherTower;
import model.IceMap;
import model.Map;
import model.MultiTower;
import model.Player;
import model.TestMap;
import model.Tower;

public class GameView extends BorderPane implements Observer{

	private Map map;
	private Canvas canvas;
	private GraphicsContext gc;
	private int ptr;
	private Player player;
	private int x, y;
	private Button nextWave;
	
	/**
	 * Creates a new gameView. This is the entirety of our towerdefense. 
	 * The idea behind this class is to create a dynamic view that updates the
	 * map that the player is on once they have won the game. 
	 */
	public GameView() {
		BorderPane pane = new BorderPane();
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D();
		player = new Player(this.gc, 100, 500);
		this.map = new TestMap(player, gc);
		this.map.spawnEnemies(5);
		this.ptr = 0; this.x = 0; this.y = 0;
		nextWave = new Button();
		nextWave.setOnAction(e -> {
			if(this.map != null && !this.map.isRunning() && this.map.getPlayer().getHealth() > 0)
				this.map.spawnEnemies(6 * this.map.getWaveCount());
		});
		
		/*
		 * Where we define what happens when we click a tower and drag it. This
		 * is going to be really disgusting and messy. I hate this >:( 
		 */
		this.setOnMouseDragged(e -> {
			if(this.map != null && this.map.isRunning()) {
				this.x = (int) e.getSceneX();
				this.y = (int) e.getSceneY();
				
				
				
			}
		});
		
		
		//showIntroCutscene();
		
		pane.setCenter(canvas);
		this.setCenter(pane);
		
		/**
		 * How we transition to the next cutscene / map. OnClick events will try to understand
		 * the game environment status (if they player is dead, all the enemies are dead, etc).
		 * going off of this information, it will reassign the program's state (map/view).
		 */
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
				
				case 0: //level 2
					if(this.map != null) {
						if(!this.map.isRunning()) {
							player.getTowers().clear();
							//showFirstCutscene();
							this.map = new IceMap(player, gc);
							this.map.spawnEnemies(1);
							this.map.show();
							ptr++;
						}
					}
					
					break;
					
				case 1: //Level 3
					if(this.map != null) {
						if(!this.map.isRunning()) {
							player.getTowers().clear();
							//showSecondCutscene();
							this.map = new TestMap(player, gc);
							this.map.spawnEnemies(1);
							this.map.show();
							ptr++;
						}
					}
					
					break;
					
				case 2: //You've won! Play the outro scene.
					if(this.map != null) {
						if(!this.map.isRunning()) {
							player.getTowers().clear();
							//showOutroCutscene();
							System.out.println("Entered outro-- click again to get back to main menu");
							ptr++;
						}
					}
					
					break;
					
				default: //If they've clicked through everything, send them back home.
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
			
			//Temporary code. Selects / places a tower on the board
			if(map != null && map.isRunning()) {
				if(e.getButton() == MouseButton.PRIMARY) {
					
					int x = (int) e.getX();
					int y = (int) e.getY();
					for(Tower t : player.getTowers()) {
						if(x < t.getLocation().getX()-20 || x > t.getLocation().getX()+20
								|| y < t.getLocation().getY()-20 || y > t.getLocation().getY()+20) {
							//NOT in bounds
							t.setSelected(false);
						} else { //must be in bounds
							t.setSelected(true);
						}
					}
				} else if(e.getButton() == MouseButton.SECONDARY) {
					System.out.println(e.getX()+"  "+e.getY());
					Tower t = new ArcherTower(new Point((int)e.getX(), (int)e.getY()));
					map.addTower(t);
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
