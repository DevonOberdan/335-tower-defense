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
import model.ArcherTower;
import model.Map;
import model.Map1;
import model.Map2;
import model.Map3;
import model.MultiTower;
import model.Player;
import model.Tower;

public class SelectorView extends StackPane implements Observer{
	
	private Map map;
	private Canvas canvas;
	private GraphicsContext gc;
	private Player player;
	private int x, y;
	private Button mainMenu, nextWave;
	
	public SelectorView() {
		
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D();
		player = new Player(this.gc, 100, 500);
		this.map = new Map1(player, gc);
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
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		//pane.setPadding(new Insets (10,10,10,10));
		//gc.drawImage(background, 0, 0);
		Label copyright = new Label ("  copyright The Team\n");
		Button easy = new Button("Easy");
		easy.setMinWidth(120);
		Button medium = new Button("Medium");
		medium.setMinWidth(120);
		Button hard = new Button("Hard");
		hard.setMinWidth(120);
		//gameView = new GameView(theGame);
		//mapSelector = new MapSelector();
		grid.add(easy, 11, 18);
		grid.add(medium, 11, 19);
		grid.add(hard, 11, 20);
		grid.add(copyright, 0, 39);
		this.getChildren().add(grid);
		//this.setCenter(grid);
		this.setId("pane");
		this.getStylesheets().addAll(this.getClass().getResource("welcomView_style.css").toExternalForm());
		this.setVisible(true);
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
		
		archerTower.setTranslateX(255);
		archerTower.setTranslateY(-125);
		archerTower.setFitHeight(80);
		archerTower.setFitWidth(70);
		
		multiTower.setTranslateX(252);
		multiTower.setTranslateY(-33);
		multiTower.setFitHeight(80);
		multiTower.setFitWidth(60);
		//this.setCenter(pane);
		nextWave.setOnAction(e -> {
			if(this.map.getRoundMode()) {
				System.out.println("Spawning enemies");
				this.map.spawnEnemies(map.getWaveCount());
				//this.map.spawnEnemies((int)(5 * this.map.getWaveCount()) + 3);
				this.map.incrementWave();
				this.map.toggleRound();
			}
				
		});
		
		mainMenu.setOnAction(e -> {
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
			this.map = new Map1(player, gc);
			this.getChildren().clear();
			this.getChildren().addAll(canvas,mainMenu,nextWave,archerTower, multiTower);
			this.map.show();
		});
		medium.setOnAction(e -> {
			this.map = new Map2(player, gc);
			this.getChildren().clear();
			this.getChildren().addAll(canvas,mainMenu,nextWave,archerTower, multiTower);
			this.map.show();
		});
		hard.setOnAction(e -> {
			this.map = new Map3(player, gc);
			this.getChildren().clear();
			this.getChildren().addAll(canvas,mainMenu,nextWave,archerTower, multiTower);
			this.map.show();
		});
		
	}

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
	
	public void unselectTowers() {
		for(Tower t : player.getTowers()) {
			t.setSelected(false);
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	 
	
}
