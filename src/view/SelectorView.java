package view;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
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
	private Button nextWave;
	private ImageView multiTower, archerTower, cannonTower;
	private Tower ctow;
	
	public SelectorView() {
		
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D();
		player = new Player(this.gc, 100, 500);
		this.map = new Map1(player, false);
		this.map.setGC(gc);
		this.x = 0; this.y = 0;
		nextWave = new Button("Next Wave");
		nextWave.setMinHeight(20);
		nextWave.setMinWidth(35);
		nextWave.setTranslateX(250);
		nextWave.setTranslateY(237);
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
		/*
		 * ARCHER TOWER IMAGE VIEW
		 */
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
		Tooltip att = new Tooltip("Archer\nDamage: 15\nRange:100\n\nPRICE: $150");
		att.setFont(new Font(20));
		Tooltip.install(archerTower, att);
		hackyMcGee(att);

		/*
		 * MULTI TOWER IMAGE VEIW
		 */
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
		Tooltip mtt = new Tooltip("Multi\nDamage: 5\nRange:100\n\nPRICE: $75");
		mtt.setFont(new Font(20));
		Tooltip.install(multiTower, mtt);
		
		
		/*
		 * CANNON TOWER IMAGE VIEW
		 */
		ImageView cannonTower = new ImageView("file:images/cannon1.png");
		Image cannonImg = new Image("file:images/cannon1.png");
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
		Tooltip ctt = new Tooltip("Cannon\nDamage: 100\nRange:80\n\nPRICE: $125");
		ctt.setFont(new Font(20));
		Tooltip.install(cannonTower, ctt);
		
		/*
		 * PLACE THE IMAGE VIEWS ON THE PLAYER PANEL
		 */
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
		
		
		nextWave.setOnAction(e -> {
			
			if(this.map != null && this.map.getEnemyList().isEmpty() && !this.map.isRunning() && this.map.getWaveCount() >= this.map.getMaxWaveCount()){
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
				this.getChildren().add((Node)(Observer) new WelcomeView(false));
				return;
			}
			
			if(this.map.getRoundMode()) {
				System.out.println("Spawning enemies");
				this.map.spawnEnemies(map.getWaveCount());
				this.map.incrementWave();
				this.map.toggleRound();
				if(this.map.getWaveCount() >= this.map.getMaxWaveCount())
					nextWave.setText("Main Menu");
			}
		});
		
		this.setOnMouseClicked(e->{
			if(this.getStylesheets().isEmpty())
				selectTower((int)e.getX(), (int)e.getY());
		});
		
		easy.setOnAction(e -> {
			this.map = new Map1(player, false);
			this.map.setGC(gc);
			this.getChildren().clear();
			this.getStylesheets().clear();
			this.getChildren().addAll(canvas, nextWave, archerTower, multiTower, cannonTower);
			this.map.show();
		});
		
		medium.setOnAction(e -> {
			this.map = new Map2(player, false);
			this.map.setGC(gc);
			this.getChildren().clear();
			this.getStylesheets().clear();
			this.getChildren().addAll(canvas, nextWave, archerTower, multiTower, cannonTower);
			this.map.show();
		});
		
		hard.setOnAction(e -> {
			this.map = new Map3(player, false);
			this.map.setGC(gc);
			this.getChildren().clear();
			this.getStylesheets().clear();
			this.getChildren().addAll(canvas, nextWave, archerTower, multiTower, cannonTower);
			this.map.show();
		});
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * ADD A PLAY/PAUSE BUTTON THAT IS ONLY ONE BUTTON AND CHANGES
		 * TEXT UPON CLICKING. CLICKING PAUSE SHOULD BRING UP AN ALERT
		 * ASKING YOU IF YOU WANT TO SAVE THE GAME
		 * IT SHOULD HAVE TWO OPTIONS, YES AND NO/CANCEL.
		 * 
		 * HITTING YES WILL SAVE; ALERT WILL CLOSE.
		 * HITTING NO/CANCEL WILL NOT SAVE; ALERT WILL CLOSE.
		 * 
		 * 
		 * ADD TOOLTIPS FOR ALL BUTTONS. TOOLTIPS FOR LIFE
		 * TOOLTIPS ARE YOUR FREND
		 * TOOLTIPS ARE UR
		 * MAMI
		 * UR PAPI
		 * UR DADI
		 * UR GRANPAPI
		 * UR GUD DOGGI
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
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
		destroyUpgradePanel();
		if(this.ctow != null) {
			Label upgradebt = new Label();
			upgradebt.setText("HOVER\nFOR\nSTATS!");
			upgradebt.setTextFill(Color.GHOSTWHITE);
			upgradebt.setFont(Font.font("Verdana", 20));
			upgradebt.setTextAlignment(TextAlignment.CENTER);
			upgradebt.setMinHeight(20);
			upgradebt.setMinWidth(35);
			upgradebt.setTranslateX(250);
			upgradebt.setTranslateY(120);
			this.getChildren().add(upgradebt);
			upgradebt.setOnMouseClicked(e -> {
				if(this.ctow.getLevel() < 3) {
					if(this.map.getPlayer().withdraw(this.ctow.getUpgradeCost()))
					{
						this.ctow.levelUp();
						createUpgradePanel();
					}	
				}
			});
			
			Tooltip tooltip = new Tooltip();
			tooltip.setAutoHide(false);
			tooltip.setOnShown(e -> {
				if(map.getPlayer().getGold() >= ctow.getUpgradeCost() && this.ctow.getLevel() < 3)
					upgradebt.setText("CLICK\nFOR\nLEVEL\nUP");
				else if(this.ctow.getLevel() < 3)
					upgradebt.setText("NEED\nFUNDS");
				else
					upgradebt.setText("MAX\nLEVEL");
				});
			tooltip.setOnHidden(e -> {
				upgradebt.setText("HOVER\nFOR\nSTATS!");
			});
			
			if(this.ctow.getLevel() < 3) {
				tooltip.setText(
					
					"\tLEVEL " + this.ctow.getLevel() + "\n" + 
					"Current range: " + this.ctow.getRange() + "\n" +
					"Next range: " + this.ctow.getRange()*1.5 + "\n" + 
					"Current damage: " + this.ctow.getDamage() + "\n" + 
					"Next damage: " + this.ctow.getDamage()*1.5 + "\n\n" + 
					"PRICE: " + this.ctow.getUpgradeCost()
				);
			} else
			{
				tooltip.setText(
				"\tLEVEL " + this.ctow.getLevel() + "\n" + 
				"Current range: " + this.ctow.getRange() + "\n" +
				"Next range: " + this.ctow.getRange()*1.5 + "\n" + 
				"Current damage: " + this.ctow.getDamage() + "\n" + 
				"Next damage: " + this.ctow.getDamage()*1.5 + "\n\n" + 
				"MAX LEVEL");
			}
			tooltip.setFont(new Font(20));
			upgradebt.setTooltip(tooltip);
		}
	}
	
	public void destroyUpgradePanel() {
		if(this.map != null) {
			this.getChildren().remove(6, this.getChildren().size());
			System.out.println(this.getChildren().size());
		}
	}
	
	public static void hackyMcGee(Tooltip tooltip) {
	    try {
	        Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
	        fieldBehavior.setAccessible(true);
	        Object objBehavior = fieldBehavior.get(tooltip);

	        Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
	        fieldTimer.setAccessible(true);
	        Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(new Duration(1)));
	    } catch (Exception e) {/*do me*/}
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
	public void show() {
		map.show();
	}
	@Override
	public void update(Observable o, Object arg) {
		
	}

	 
	
}
