package view;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Map;
import model.Map1;
import model.Map2;
import model.Map3;
import model.Player;
import model.tower.ArcherTower;
import model.tower.CannonTower;
import model.tower.MultiTower;
import model.tower.Tower;

/**
 * This is casual mode. A user can select a sequence of maps (easy, medium, 
 * hard), and play for as long as they'd like. There are NO WAVE LIMITs for
 * this mode. The user can play on a map until they grow old and die.
 * 
 * Main menu button will destroy everything here and send us back to the homepage.
 * 
 * @author Taite Nazifi
 *
 */
public class SelectorView extends StackPane implements Observer{
	
	private Map map;
	private Canvas canvas;
	private GraphicsContext gc;
	private Player player;
	private int x, y;
	private Button mainMenu, nextWave;
	private ImageView multiTower, archerTower, cannonTower;
	private Tower ctow;
	
	public SelectorView() {
		
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D();
		player = new Player(this.gc, 100, 500);
		this.map = new Map1(player);
		this.map.setGC(gc);
		this.x = 0; this.y = 0;
		nextWave = new Button("Next Wave");
		nextWave.setMinHeight(20);
		nextWave.setMinWidth(35);
		nextWave.setTranslateX(250);
		nextWave.setTranslateY(202); 
		mainMenu = new Button("Main Menu");
		mainMenu.setMinHeight(20);
		mainMenu.setMinWidth(35);
		mainMenu.setTranslateX(250);
		mainMenu.setTranslateY(237);
		this.ctow = null;
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		Label copyright = new Label ("  copyright The Team\n");
		Button easy = new Button("Easy");
		easy.setMinWidth(40);
		Button medium = new Button("Medium");
		medium.setMinWidth(60);
		Button hard = new Button("Hard");
		hard.setMinWidth(40);
		grid.add(easy, 19, 19);
		grid.add(medium, 7, 36);
		grid.add(hard, 2, 6);
		grid.add(copyright, 0, 39);
		this.getChildren().add(grid);
		this.setId("pane");
		this.getStylesheets().addAll(this.getClass().getResource("welcomView_style.css").toExternalForm());
		this.setVisible(true);

		/*
		 * Where we define what happens when we click a tower and drag it. This
		 * is going to be really disgusting and messy. I hate this >:( 
		 */
		//Archer tower image, where the archer tower is drawn and assigned event handlers.
		archerTower = new ImageView("file:images/archer1.png");
		Image archerimg = new Image("file:images/archer1.png");
		archerTower.setOnMouseReleased(e -> {
			if(this.map.mapFinished() || (e.getSceneX() >= 470 || e.getSceneX() <= 25 || e.getSceneY() >= 470 || e.getSceneY() <= 25)) {
				this.map.setDragged(null, false, 0, 0);
				return;
			}
			
			Tower t = new ArcherTower(new Point((int)e.getSceneX(), (int)e.getSceneY()));
			if(selectTower((int)e.getSceneX(), (int)e.getSceneY()) == null)
			{
				System.out.println((int)e.getSceneX() + " " +(int)e.getSceneY());
				map.addTower(t);
			}
			this.map.setDragged(null, false, 0, 0);
		});
		archerTower.setOnMouseDragged(e -> {
			if(this.map != null && !this.map.mapFinished())
				this.map.setDragged(archerimg, true, (int)e.getSceneX(), (int)e.getSceneY());
		});
		
		//multi tower image, where the multi tower is drawn and assigned event handlers.
		multiTower = new ImageView("file:images/MultiTower1.png");
		Image multiimg = new Image("file:images/MultiTower1.png");
		multiTower.setOnMouseReleased(e -> {
			if(this.map.mapFinished() || (e.getSceneX() >= 470 || e.getSceneX() <= 25 || e.getSceneY() >= 470 || e.getSceneY() <= 25)) {
				this.map.setDragged(null, false, 0, 0);
				return;
			}
			
			Tower t = new MultiTower(new Point((int)e.getSceneX(), (int)e.getSceneY()));
			if(selectTower((int)e.getSceneX(), (int)e.getSceneY()) == null)
			{
				System.out.println((int)e.getSceneX() + " " +(int)e.getSceneY());
				map.addTower(t);
			}
			this.map.setDragged(null, false, 0, 0);
		});
		
		multiTower.setOnMouseDragged(e -> {
			if(this.map != null && !this.map.mapFinished())
				this.map.setDragged(multiimg, true, (int)e.getSceneX(), (int)e.getSceneY());
		});
		
		cannonTower = new ImageView("file:images/cannon.png");
		Image cannonImg = new Image("file:images/cannon.png");
		cannonTower.setOnMouseReleased(e -> {
			if(this.map.mapFinished() || (e.getSceneX() >= 470 || e.getSceneX() <= 25 || e.getSceneY() >= 470 || e.getSceneY() <= 25)) {
				this.map.setDragged(null, false, 0, 0);
				return;
			}
			
			Tower t = new CannonTower(new Point((int)e.getSceneX(), (int)e.getSceneY()));
			if(selectTower((int)e.getSceneX(), (int)e.getSceneY()) == null)
			{
				System.out.println((int)e.getSceneX() + " " +(int)e.getSceneY());
				map.addTower(t);
			}
			this.map.setDragged(null, false, 0, 0);
		});
		cannonTower.setOnMouseDragged(e -> {
			if(this.map != null && !this.map.mapFinished())
				this.map.setDragged(cannonImg, true, (int)e.getSceneX(), (int)e.getSceneY());
		});
		
		archerTower.setTranslateX(255);
		archerTower.setTranslateY(-165);
		archerTower.setFitHeight(60);
		archerTower.setFitWidth(50);
		
		multiTower.setTranslateX(252);
		multiTower.setTranslateY(-93);
		multiTower.setFitHeight(60);
		multiTower.setFitWidth(40);
		
		cannonTower.setTranslateX(252);
		cannonTower.setTranslateY(-20);
		cannonTower.setFitHeight(60);
		cannonTower.setFitWidth(50);
		
		
		
		//this.setCenter(pane);
		nextWave.setOnAction(e -> {
			
			if(this.map.getRoundMode()) {
				System.out.println("Spawning enemies");
				this.map.spawnEnemies(map.getWaveCount());
				this.map.incrementWave();
				this.map.toggleRound();
			}
				
		});
		
		mainMenu.setOnAction(e -> {
			easy.setOnAction(null);
			medium.setOnAction(null);
			hard.setOnAction(null);
			archerTower = null;
			multiTower = null;
			this.getChildren().clear();
			this.setOnMouseClicked(null);
			this.map.destroyitall();
			this.player=null;
			this.canvas = null;
			this.gc = null;
			this.getChildren().add((Node)(Observer) new WelcomeView());
		});
		this.setOnMouseClicked(e->{
			Tower t = selectTower((int)e.getX(), (int)e.getY());
			if(t != null) {
				//add upgrade / sell obj
			}
		});
		
		easy.setOnAction(e -> {
			this.map = new Map1(player);
			this.map.setGC(gc);
			this.getChildren().clear();
			this.getStylesheets().clear();
			this.getChildren().addAll(canvas,mainMenu,nextWave,archerTower, multiTower, cannonTower);
			this.map.show();
		});
		medium.setOnAction(e -> {
			this.map = new Map2(player);
			this.map.setGC(gc);
			this.getChildren().clear();
			this.getStylesheets().clear();
			this.getChildren().addAll(canvas,mainMenu,nextWave,archerTower, multiTower, cannonTower);
			this.map.show();
		});
		hard.setOnAction(e -> {
			this.map = new Map3(player);
			this.map.setGC(gc);
			this.getChildren().clear();
			this.getStylesheets().clear();
			this.getChildren().addAll(canvas,mainMenu,nextWave,archerTower, multiTower, cannonTower);
			this.map.show();
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
			if(x < t.getLocation().getX()-25 || x > t.getLocation().getX()+25
					|| y < t.getLocation().getY()-25 || y > t.getLocation().getY()+25) {
				//NOT in bounds
				t.setSelected(false);
				this.ctow = null;
			} else { //must be in bounds
				t.setSelected(true);
				this.ctow = t;
				break;
			}
		}
		if(this.ctow != null) {
			createUpgradePanel();
		} else {
			destroyUpgradePanel();
		}
		return this.ctow;
	}
	
	public void createUpgradePanel() {
		if(this.ctow != null && this.ctow.getLevel() < 3) {
			Button upgradebt = new Button("Upgrade!");
			upgradebt.setMinHeight(20);
			upgradebt.setMinWidth(35);
			upgradebt.setTranslateX(250);
			upgradebt.setTranslateY(165);
			this.getChildren().add(upgradebt);
			upgradebt.setOnAction(e -> {
				if(this.map.getPlayer().withdraw(this.ctow.getUpgradeCost()))
					this.ctow.levelUp();
			});
		}
	}
	
	public void destroyUpgradePanel() {
		if(this.ctow != null) {
			this.ctow.setSelected(false);
			this.ctow = null;
		}
			this.getChildren().remove(6, this.getChildren().size());
			System.out.println(this.getChildren().size());
	}
	
	/**
	 * unselects all towers on the map.
	 */
	public void unselectTowers() {
		for(Tower t : player.getTowers()) {
			t.setSelected(false);
		}
		this.ctow = null;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	 
	
}
