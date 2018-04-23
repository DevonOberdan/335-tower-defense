package view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import model.ArcherTower;
import model.Map;
import model.Map1;
import model.Map2;
import model.Map3;
import model.MultiTower;
import model.Player;
import model.RandomTower;
import model.Tower;

/**
 * Game view
 * @author Taite Nazifi
 *
 */
public class GameView extends StackPane implements Observer{

	private Map map;
	private Canvas canvas;
	private GraphicsContext gc;
	private int ptr;
	private Player player;
	private int x, y;
	private Button nextRound, nextWave;
	/**
	 * Creates a new gameView. This is the entirety of our towerdefense. 
	 * The idea behind this class is to create a dynamic view that updates the
	 * map that the player is on once they have won the game. 
	 */
	public GameView() {
		//StackPane pane = new StackPane();
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D();
		player = new Player(this.gc, 100, 500);
		this.map = new Map1(player, gc);
		this.ptr = 0; this.x = 0; this.y = 0;
		nextWave = new Button("Next Wave");
		nextWave.setMinHeight(20);
		nextWave.setMinWidth(35);
		nextWave.setTranslateX(250);
		nextWave.setTranslateY(202);
		nextRound = new Button("Next Round");
		nextRound.setMinHeight(20);
		nextRound.setMinWidth(35);
		nextRound.setTranslateX(250);
		nextRound.setTranslateY(237);
		//this.getChildren().add((Node) new MenuView());
		/*
		 * Where we define what happens when we click a tower and drag it. This
		 * is going to be really disgusting and messy. I hate this >:( 
		 */
		ImageView archerTower = new ImageView("file:images/archer1.png");
		Image archerimg = new Image("file:images/archer1.png");
		archerTower.setOnMouseReleased(e -> {
			if(this.map.mapFinished())
				return;
			Tower t = new ArcherTower(new Point((int)e.getSceneX(), (int)e.getSceneY()));
			if(selectTower((int)e.getSceneX(), (int)e.getSceneY()) == null)
			{
				System.out.println((int)e.getSceneX() + " " +(int)e.getSceneY());
				map.addTower(t);
			}
		});
		archerTower.setOnMouseDragged(e -> {
			if(this.map != null && !this.map.mapFinished()) {
				this.x = (int) e.getSceneX();
				this.y = (int) e.getSceneY();
				gc.drawImage(archerimg, 0, 0, 150, 150, x-30, y-40, 60, 80);
			}
		});
		
		ImageView multiTower = new ImageView("file:images/MultiTower1.png");
		Image multiimg = new Image("file:images/MultiTower1.png");
		multiTower.setOnMouseReleased(e -> {
			if(this.map.mapFinished())
				return;
			Tower t = new MultiTower(new Point((int)e.getSceneX(), (int)e.getSceneY()));
			if(selectTower((int)e.getSceneX(), (int)e.getSceneY()) == null)
			{
				System.out.println((int)e.getSceneX() + " " +(int)e.getSceneY());
				map.addTower(t);
			}
		});
		multiTower.setOnMouseDragged(e -> {
			if(this.map != null && !this.map.mapFinished()) {
				this.x = (int) e.getSceneX();
				this.y = (int) e.getSceneY();
				gc.drawImage(multiimg, 0, 0, 60, 80, x-30, y-40, 60, 80);
			}
		});
		
		ImageView randomTower = new ImageView("file:images/random.png");
		Image randomimg = new Image("file:images/random.png");
		randomTower.setOnMouseReleased(e -> {
			if(this.map.mapFinished())
				return;
			Tower t = new RandomTower(new Point((int)e.getSceneX(), (int)e.getSceneY()));
			if(selectTower((int)e.getSceneX(), (int)e.getSceneY()) == null)
			{
				System.out.println((int)e.getSceneX() + " " +(int)e.getSceneY());
				map.addTower(t);
			}
		});
		randomTower.setOnMouseDragged(e -> {
			if(this.map != null && !this.map.mapFinished()) {
				this.x = (int) e.getSceneX();
				this.y = (int) e.getSceneY();
				gc.drawImage(randomimg, 0, 0, 150, 125, x-30, y-40, 60, 80);
			}
		});
		
		archerTower.setTranslateX(255);
		archerTower.setTranslateY(-125);
		archerTower.setFitHeight(80);
		archerTower.setFitWidth(70);
		
		multiTower.setTranslateX(252);
		multiTower.setTranslateY(-33);
		multiTower.setFitHeight(80);
		multiTower.setFitWidth(60);
		
		randomTower.setTranslateX(255);
		randomTower.setTranslateY(50);
		randomTower.setFitHeight(80);
		randomTower.setFitWidth(70);
		
		this.getChildren().addAll(canvas,nextRound,nextWave,archerTower, multiTower, randomTower);
		//this.setCenter(pane);
		nextWave.setOnAction(e -> {
			if(this.map.getRoundMode()) {
				System.out.println("Spawning enemies");
				this.map.spawnEnemies(this.map.getWaveCount());
				//this.map.spawnEnemies((int)(5 * this.map.getWaveCount()) + 3);
				this.map.incrementWave();
				this.map.toggleRound();
			}
				
		});
		
		/*
		 * How we transition to the next cutscene / map. OnClick events will try to understand
		 * the game environment status (if they player is dead, all the enemies are dead, etc).
		 * going off of this information, it will reassign the program's state (map/view).
		 */
		nextRound.setOnAction(e ->{
			if(this.player.isDead()) {
				this.getChildren().clear();
				//this.setCenter(null);
				this.setOnMouseClicked(null);
				this.map = null;
				this.canvas = null;
				this.gc = null;
				this.getChildren().add((Node)(Observer) new WelcomeView());
				//this.setCenter((Node)(Observer) new WelcomeView());
				return;
			}
			
			if(this.map != null && !this.map.isRunning() && this.map.getWaveCount() >= this.map.getMaxWaveCount()){
				switch(ptr) {
				
				case 0: //level 2
					if(this.map != null) {
						if(!this.map.isRunning()) {
							player.getTowers().clear();
							this.map.getTimeline().stop();
							//showFirstCutscene();
							this.map = new Map2(player, gc);
							//this.map.spawnEnemies(5);
							this.map.show();
							ptr++;
						}
					}
					
					break;
					
				case 1: //Level 3
					if(this.map != null) {
						if(!this.map.isRunning()) {
							player.getTowers().clear();
							this.map.getTimeline().stop();
							//showSecondCutscene();
							this.map = new Map3(player, gc);
							//this.map.spawnEnemies(5);
							this.map.show();
							ptr++;
						}
					}
					
					break;
					
				case 2: //You've won! Play the outro scene.
					if(this.map != null) {
						if(!this.map.isRunning()) {
							player.getTowers().clear();
							this.map.getTimeline().stop();
							//showOutroCutscene();
							System.out.println("Entered outro-- click again to get back to main menu");
							ptr++;
						}
					}
					
					break;
					
				default: //If they've clicked through everything, send them back home.
					if(!this.map.isRunning()) {
						this.getChildren().clear();
						//this.setCenter(null);
						this.setOnMouseClicked(null);
						this.map = null;
						this.canvas = null;
						this.gc = null;
						this.getChildren().add((Node)(Observer) new WelcomeView());
					}
					break;
				}
			} else if(this.map != null){
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("Enemies inbound!");
				a.setHeaderText(null);
				a.setContentText("You must complete all of the waves\nbefore advancing to the next round.\nYou have completed " + this.map.getWaveCount() + " out of " + this.map.getMaxWaveCount() + " rounds.");
				a.show();
			}
		});
		
		this.setOnMouseClicked(e->{
			Tower t = selectTower((int)e.getX(), (int)e.getY());
			if(t != null) {
				//add upgrade / sell obj
			}
		});
	}
	
	/**
	 * Attempts to select a given tower at x y on the map
	 * if there are no towers there, it returns false. if it
	 * successfully selects a tower, it returns true.
	 * @param x
	 * @param y
	 * @return
	 */
	
	public Tower selectTower(int x, int y) {
		unselectTowers();
		for(Tower t : player.getTowers()) {
			if(x < t.getLocation().getX()-20 || x > t.getLocation().getX()+20
					|| y < t.getLocation().getY()-20 || y > t.getLocation().getY()+20) {
				//NOT in bounds
				t.setSelected(false);
			} else { //must be in bounds
				t.setSelected(true);
				return t;
			}
		}
		return null;
	}
	
	/**
	 * unselects all towers on the map.
	 */
	public void unselectTowers() {
		for(Tower t : player.getTowers()) {
			t.setSelected(false);
		}
	}
	
	public void show() {
		map.show();
	}
	@Override
	public void update(Observable o, Object arg) {
		
	}


}

