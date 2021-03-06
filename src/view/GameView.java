package view;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.function.BiFunction;

import com.sun.javafx.tk.Toolkit.Task;
import com.sun.media.jfxmedia.AudioClip;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.Map;
import model.Map1;
import model.Map1_Path;
import model.Map2;
import model.Map3;
import model.Path;
import model.Player;
import model.enemy.Enemy;
import model.tower.ArcherTower;
import model.tower.CannonTower;
import model.tower.MultiTower;
import model.tower.Tower;

/**
 * This is the campaign. This will cycle through each map based
 * on whether or not the player has defeated the current amount of waves for the current
 * map. If the user defeats all of the waves for all of the maps, 
 * they are congratulated and sent back to the home page.
 * 
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
	private Button nextRound;
	private ImageView archerTower, multiTower, cannonTower;
	private Tower ctow;
	private Enemy cen;
	/**
	 * Creates a new gameView. This is the entirety of our towerdefense. 
	 * The idea behind this class is to create a dynamic view that updates the
	 * map that the player is on once they have won the game. 
	 * 
	 * @author Taite Nazifi
	 */
	
	
	/**
	 * Creates a new gameView. This is the entirety of our towerdefense. 
	 * The idea behind this class is to create a dynamic view that updates the
	 * map that the player is on once they have won the game. 
	 * 
	 * @author Taite Nazifi
	 */
	public GameView() {
		//StackPane pane = new StackPane();
		canvas = new Canvas (580,500);
		gc = canvas.getGraphicsContext2D();
		player = new Player(this.gc, 100, 500);
		this.map = new Map1(player);
		this.map.setGC(gc);
		this.ptr = 0; this.x = 0; this.y = 0;
		nextRound = new Button("Next Wave");
		nextRound.setMinHeight(20);
		nextRound.setMinWidth(35);
		nextRound.setTranslateX(250);
		nextRound.setTranslateY(237);

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

			Path tmpPath = map.getPath();
			Point newLoc = new Point((int)e.getSceneX(), (int)e.getSceneY());
			
			if (!tmpPath.canPlace(newLoc))
				return;
			
			Tower t = new ArcherTower(newLoc);
			
			if(!selectEntity((int)e.getSceneX(), (int)e.getSceneY()))
			{
				//if(this.map.inBounds((int)e.getScenex(), (int)e.getSceneY()))
				map.addTower(t);
			}
			
			this.map.setDragged(null, false, 0, 0);
		});
		archerTower.setOnMouseDragged(e -> {
			Path tmpPath = map.getPath();
			Point newLoc = new Point((int)e.getSceneX(), (int)e.getSceneY());
			if(this.map != null && !this.map.mapFinished() && tmpPath.canPlace(newLoc)) 
				this.map.setDragged(archerimg, true, (int)e.getSceneX(), (int)e.getSceneY());
		});
		Tooltip att = new Tooltip("Archer\nDamage: 15\nRange:100\n\nPRICE: $150");
		att.setFont(new Font(20));
		Tooltip.install(archerTower, att);
		hackyMcGee(att);

		/*
		 * MULTI TOWER IMAGE VEIW
		 */
		multiTower = new ImageView("file:images/multi1.png");
		Image multiimg = new Image("file:images/multi1.png");
		multiTower.setOnMouseReleased(e -> {
			
			if(this.map.mapFinished() || (e.getSceneX() >= 470 || e.getSceneX() <= 25 || e.getSceneY() >= 470 || e.getSceneY() <= 25)) {
				this.map.setDragged(null, false, 0, 0);
				return;
			}
			Path tmpPath = map.getPath();
			Point newLoc = new Point((int)e.getSceneX(), (int)e.getSceneY());
			
			if (!tmpPath.canPlace(newLoc))
				return;
			Tower t = new MultiTower(new Point((int)e.getSceneX(), (int)e.getSceneY()));
			
			if(!selectEntity((int)e.getSceneX(), (int)e.getSceneY()))
			{
				//if(this.map.inBounds((int)e.getScenex(), (int)e.getSceneY()))
				map.addTower(t);
			}
			
			this.map.setDragged(null, false, 0, 0);

		});
		multiTower.setOnMouseDragged(e -> {
			Path tmpPath = map.getPath();
			Point newLoc = new Point((int)e.getSceneX(), (int)e.getSceneY());
			if(this.map != null && !this.map.mapFinished() && tmpPath.canPlace(newLoc)) 
				this.map.setDragged(multiimg, true, (int)e.getSceneX(), (int)e.getSceneY());
		});
		Tooltip mtt = new Tooltip("Multi\nDamage: 5\nRange:100\n\nPRICE: $75");
		mtt.setFont(new Font(20)); 
		Tooltip.install(multiTower, mtt);
		
		
		/*
		 * CANNON TOWER IMAGE VIEW
		 */
		cannonTower = new ImageView("file:images/cannon1.png");
		Image cannonImg = new Image("file:images/cannon1.png");
		cannonTower.setOnMouseReleased(e -> {
			
			if(this.map.mapFinished() || (e.getSceneX() >= 470 || e.getSceneX() <= 25 || e.getSceneY() >= 470 || e.getSceneY() <= 25)) {
				this.map.setDragged(null, false, 0, 0);
				return;
			}
			
			Path tmpPath = map.getPath();
			Point newLoc = new Point((int)e.getSceneX(), (int)e.getSceneY());
			
			if (!tmpPath.canPlace(newLoc))
				return;
			
			Tower t = new CannonTower(new Point((int)e.getSceneX(), (int)e.getSceneY()));
			if(!selectEntity((int)e.getSceneX(), (int)e.getSceneY()))
			{
				//if(this.map.inBounds((int)e.getScenex(), (int)e.getSceneY()))
				map.addTower(t);
			}
			this.map.setDragged(null, false, 0, 0);
		});
		cannonTower.setOnMouseDragged(e -> {
			Path tmpPath = map.getPath();
			Point newLoc = new Point((int)e.getSceneX(), (int)e.getSceneY());
			if(this.map != null && !this.map.mapFinished() && tmpPath.canPlace(newLoc)) 
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
		
		Button playpause = new Button("Pause");
		playpause.setTranslateX(0);
		playpause.setTranslateY(-237.5);
		playpause.setMinWidth(30);
		playpause.setOnAction(e -> {
			if(playpause.getText().equals("Pause")) {
				for(Tower t : this.player.getTowers()) {
					if(t.isAnimating())	
						t.endTimers();
				}
				this.map.pause();
				playpause.setText("Unpause");
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setTitle("Save Game?");
				a.setHeaderText("Press 'OK' to save your game.");
				a.setContentText("Press 'CANCEL' to remain paused.");
				Optional<ButtonType> result = a.showAndWait();
				if(result.get() == ButtonType.OK) {
					try {
				    	File towerfile = new File("state/towers");
				    	File mapfile = new File("state/map");
				    	File playerfile = new File("state/player");
				    	towerfile.createNewFile();
				    	mapfile.createNewFile();
				    	playerfile.createNewFile();
				        FileOutputStream towerstream = new FileOutputStream(towerfile);
				        FileOutputStream mapstream = new FileOutputStream(mapfile);        
				        FileOutputStream playerstream = new FileOutputStream(playerfile);
				        ObjectOutputStream towerout = new ObjectOutputStream(towerstream);
				        ObjectOutputStream mapout = new ObjectOutputStream(mapstream);
				        ObjectOutputStream playerout = new ObjectOutputStream(playerstream);
				        ArrayList<Tower> towers = new ArrayList<Tower>();
				        for (Tower t : this.player.getTowers()) {
				        	towers.add(t);
				        }
				       // towerout.writeObject(towers);
			        	List<Enemy> enlis = new ArrayList<>();
				        if(this.map.getEnemyCount() > 0) {
				        	for(Enemy en : this.map.getEnemyList()) {
				        		enlis.add(en);
				        	}
				        	this.map.getEnemyList().clear();
				        }
				        mapout.writeObject(this.map);
				        playerout.writeObject(this.player);
				        
				        towerout.close();
				        mapout.close();
				        playerout.close();
				        
				        this.map.getEnemyList().addAll(enlis);
				        
					 } catch (IOException e1) {
					      e1.printStackTrace();
					 }
				}
			} else {
				this.map.play();
				playpause.setText("Pause");
			}
		});
		
		this.getChildren().addAll(canvas,nextRound,archerTower, multiTower, cannonTower, playpause);
		//this.setCenter(pane);
		
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
				return;
			}
			if(this.map.getRoundMode()) {
				this.map.spawnEnemies(this.map.getWaveCount());
				this.map.incrementWave();
				this.map.toggleRound();
				if(this.map.getWaveCount() == this.map.getMaxWaveCount())
					nextRound.setText("Next map");
				return;
			}
			
			if(this.map != null && this.map.getEnemyList().isEmpty() && !this.map.isRunning() && this.map.getWaveCount() >= this.map.getMaxWaveCount()){
				switch(ptr) {
				
				case 0: //level 2
					if(this.map != null) {
						if(!this.map.isRunning()) {
							for(Tower t : this.player.getTowers()) {
								if(t.isAnimating())	
									t.endTimers();
							}
							player.getTowers().clear();
							this.map.getTimeline().stop();
							//showFirstCutscene();
							this.map = new Map2(player);
							this.map.setGC(gc);
							//this.map.spawnEnemies(5);
							this.map.show();
							ptr++;
							nextRound.setText("Next Round");
						}
					}
					
					break;
					
				case 1: //Level 3
					if(this.map != null) {
						if(!this.map.isRunning()) {
							for(Tower t : this.player.getTowers()) {
								if(t.isAnimating())	
									t.endTimers();
							}
							player.getTowers().clear();
							this.map.getTimeline().stop();
							//showSecondCutscene();
							this.map = new Map3(player);
							this.map.setGC(gc);
							//this.map.spawnEnemies(5);
							this.map.show();
							ptr++;
							nextRound.setText("Next Round");
						}
					}
					
					break;
					
				case 2: //You've won! Play the outro scene.
					if(this.map != null) {
						nextRound.setText("Main Menu");
						if(!this.map.isRunning()) {
							for(Tower t : this.player.getTowers()) {
								if(t.isAnimating())	
									t.endTimers();
							}
							player.getTowers().clear();
							this.map.getTimeline().stop();
							//showOutroCutscene();
							ptr++;
							this.getChildren().clear();
							this.getStylesheets().clear();
							this.setOnMouseClicked(null);
							this.map = null;
							this.canvas = null;
							this.gc = null;
							this.getChildren().add((Node)(Observer) new WelcomeView());
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
			}
		});
		
		this.setOnMouseClicked(e->{
			selectEntity((int)e.getX(), (int)e.getY());
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
	public boolean selectEntity(int x, int y) {
		unselectTowers();
		unselectEnemies();
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
			return true;
		} else {
			destroyUpgradePanel();
		}

		for(Enemy e : this.map.getEnemyList()) {
			if(x < e.getLoc().getX()-25 || x > e.getLoc().getX()+25 || y < e.getLoc().getY()-25 || y > e.getLoc().getY()+25) {
				//not in bounds.
				this.cen = null;
				e.setSelected(false);
			}
			else {
				this.cen = e;
				e.setSelected(true);
				break;
			}
		}
		if(this.cen != null) {
			createEnemyPanel();
			return true;
		} else {
			destroyEnemyPanel();
		}
		return false;
	}
	public void destroyEnemyPanel() {
		this.getChildren().remove(6, this.getChildren().size());
	}
	public void createEnemyPanel() {
		destroyUpgradePanel();
		if(this.cen != null) {
			Label upgradebt = new Label();
			upgradebt.setText("HOVER\nFOR\nSTATS!");
			upgradebt.setTextFill(Color.GHOSTWHITE);
			upgradebt.setFont(Font.font("Verdana", 20));
			upgradebt.setTextAlignment(TextAlignment.CENTER);
			upgradebt.setTranslateX(250);
			upgradebt.setTranslateY(120);
			this.getChildren().add(upgradebt);
			Tooltip tooltip = new Tooltip();
			tooltip.setAutoHide(false);
			tooltip.setText(
				
				this.cen.getName() + "\n" + 
				"SPEED: " + this.cen.getSpeed() + "\n" +
				"HEALTH: " + this.cen.getHel() + "\n" + 
				"DAMAGE: " + this.cen.getDamage() + "\n" + 
				"REWARD: " + this.cen.getReward() + "\n\n"				
			);
			tooltip.setTextAlignment(TextAlignment.CENTER);
			tooltip.setFont(new Font(20));
			upgradebt.setTooltip(tooltip);
		}
	}
	
	public void createUpgradePanel() {
		destroyUpgradePanel();
		if(this.ctow != null) {
			Label upgradebt = new Label();
			upgradebt.setText("HOVER\nFOR\nSTATS!");
			upgradebt.setTextFill(Color.GHOSTWHITE);
			upgradebt.setFont(Font.font("Verdana", 20));
			upgradebt.setTextAlignment(TextAlignment.CENTER);
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
				if(map.getPlayer().getGold() >= ctow.getUpgradeCost())
					upgradebt.setText("CLICK\nFOR\nLEVEL\nUP");
				else
					upgradebt.setText("NEED\nFUNDS");
					
				});
			tooltip.setOnHidden(e -> {
				upgradebt.setText("HOVER\nFOR\nSTATS!");
			});
			tooltip.setText(
				
				"\tLEVEL " + this.ctow.getLevel() + "\n" + 
				"Current range: " + this.ctow.getRange() + "\n" +
				"Next range: " + this.ctow.getRange()*1.5 + "\n" + 
				"Current damage: " + this.ctow.getDamage() + "\n" + 
				"Next damage: " + this.ctow.getDamage()*1.5 + "\n\n" + 
				"PRICE: " + this.ctow.getUpgradeCost()
				
			);
			tooltip.setFont(new Font(20));
			upgradebt.setTooltip(tooltip);
			
			
			/*
			 * create sell label
			 */
			
			Label sellbt = new Label();
			sellbt.setText("Sell");
			sellbt.setTextFill(Color.GHOSTWHITE);
			sellbt.setFont(Font.font("Verdana", 20));
			sellbt.setTextAlignment(TextAlignment.CENTER);
			sellbt.setTranslateX(250);
			sellbt.setTranslateY(200);
			this.getChildren().add(sellbt);
			sellbt.setOnMouseClicked(e -> {
				this.player.deposit((int)(this.ctow.getCost()*0.5), null);
				this.player.getTowers().remove(ctow);
				this.map.getTowerList().remove(ctow);
			});
			Tooltip selltt = new Tooltip();
			selltt.setAutoHide(false);
			selltt.setText(
				"Sell for: " + (int)(this.ctow.getCost()*0.5)
			);
			selltt.setFont(new Font(20));
			sellbt.setTooltip(selltt);
			
		}
	}
	
	public void destroyUpgradePanel() {
			this.getChildren().remove(6, this.getChildren().size());
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
	
	 
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public void setMap(Map m) {
		this.map = m;
	}
	
	public GraphicsContext getgc() {
		return this.gc; 
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
	
	public void unselectEnemies() {
		for(Enemy e : this.map.getEnemyList()) {
			e.setSelected(false);
		}
		this.cen = null;
	}
	public void show() {
		map.show();
	}
	@Override
	public void update(Observable o, Object arg) {
		
	}

	public void playSong() {
		File dir = new File("sounds/L_.mp3");
		Media media = new Media(dir.toURI().toString());
		MediaPlayer player = new MediaPlayer(media);
		player.play();
		 
		 player.setOnEndOfMedia(new Runnable () {

				@Override
				public void run() {
					
					player.stop();
					player.play();
				}
				  
			  });
	}
}