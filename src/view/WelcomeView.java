package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Map;
import model.Player;
import model.tower.Tower;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 * Main menu! This view is for welcoming our players to a bunch of options
 * that they can choose to get the game going. Casual is where you can pick from the three maps, easy medium and hard.
 * campaign is a story-line, iterating through each map and progressing to the end.
 * @author Taite Nazifi
 *
 */
public class WelcomeView extends StackPane implements Observer{
	private Observer gameView, instructionView, currentView, selectorView;
	public List<MediaPlayer> players = new ArrayList<>();
	public WelcomeView() {
		playSong();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		//pane.setPadding(new Insets (10,10,10,10));
		//gc.drawImage(background, 0, 0);
		Label copyright = new Label ("  copyright The Team\n");
		Button casual = new Button("Casual");
		casual.setMinWidth(120);
		Button loadGame = new Button("Load Game");
		loadGame.setMinWidth(120);
		Button instructions = new Button("Instructions");
		instructions.setMinWidth(120);
		Button campaign = new Button("Campaign");
		campaign.setMinWidth(120);
		grid.add(campaign, 11, 18);
		grid.add(casual, 11, 19);
		grid.add(loadGame, 11, 20);
		grid.add(instructions, 11, 21);
		grid.add(copyright, 0, 39);
		campaign.setOnAction(e -> {
			this.getStylesheets().clear();
		    this.getChildren().clear();
		    campaign.setOnAction(null);
		    instructions.setOnAction(null);
		    casual.setOnAction(null);
			gameView = new GameView();
			setViewTo(gameView);
			((GameView) gameView).show();
			System.out.println("Game View"); 
			players.get(0).stop();
		});
		casual.setOnAction(e -> {
			this.getStylesheets().clear();
		    this.getChildren().clear();
		    campaign.setOnAction(null);  
		    instructions.setOnAction(null);
		    //this.setCenter(null); // set the center of pane to null
		    this.getChildren().add((Node) new SelectorView(this));
			//((SelectorView)selectorView).show();
		    casual.setOnAction(null);
			System.out.println("Map selector");
		}); 
		instructions.setOnAction(e -> {
			instructionView = new InstructionView();
			setViewTo(instructionView);
			System.out.println("Instruction View");
			
		});
		loadGame.setOnAction(e -> {
			this.getStylesheets().clear();
		    this.getChildren().clear();
		    campaign.setOnAction(null);
		    instructions.setOnAction(null);
		    casual.setOnAction(null);
		    loadGame(); 
		});
		this.getChildren().add(grid);
		//this.setCenter(grid);
		this.setId("pane");
		this.getStylesheets().addAll(this.getClass().getResource("welcomView_style.css").toExternalForm());
		this.setVisible(true);
	}
	
	/**
	 * Reads in the files from the saved states and loads
	 * the game according to match what was saved in the state.
	 */
	public void loadGame() {
		try {
//    	File towerfile = new File("state/towers");
    	File mapfile = new File("state/map");
    	File playerfile = new File("state/player");
//        FileInputStream towerstream = new FileInputStream(towerfile);
        FileInputStream mapstream = new FileInputStream(mapfile);        
        FileInputStream playerstream = new FileInputStream(playerfile);
//       ObjectInputStream towerin = new ObjectInputStream(towerstream);
        ObjectInputStream mapin = new ObjectInputStream(mapstream);
        ObjectInputStream playerin = new ObjectInputStream(playerstream);
        Player player = (Player) playerin.readObject();
        Map map = (Map) mapin.readObject(); 
//        ArrayList<Tower> towers = (ArrayList<Tower>) towerin.readObject();

//        towerin.close();
        mapin.close();
        playerin.close();
        gameView = new GameView();
        GameView gv = (GameView) gameView;
        if(map.getWaveCount() != 0 && !map.getRoundMode()) {
        	map.decrementWave();
            map.setRoundMode(true);
        }
        map.getEnemyList().clear();
        gv.setMap(map);
        gv.setPlayer(player);
		setViewTo(gameView);
		System.out.println("Game View"); 
		players.get(0).stop();
		map.resetBackground();
		map.resetMenu();
		map.resetTimeline();
		map.setGC(gv.getgc());
		for(Tower t : player.getTowers()) {
			t.setGC(gv.getgc());
			t.reset();
		}
		player.reset();
		player.setGC(gv.getgc());
		map.setPlayer(player);
		((GameView) gameView).show();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * setViewTo sets the current view of the application to newView,
	 * allowing us to swap between views willy-nilly.
	 * 
	 * @param newView the view we want to change to.
	 * @author The Team
	 */
	  public void setViewTo(Observer newView) {
			this.getStylesheets().clear();
		    this.getChildren().clear();
		    //this.setCenter(null); // set the center of pane to null
		    currentView = newView; // update the current view to the input observer
		    this.getChildren().add((Node) currentView);
		    //this.setCenter((Node) currentView); // set the center of the pane to the current observer
	  }

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	public void playSong() {
		System.out.println("play song");
		File dir = new File("sounds/main_menu.mp3");
		Media media = new Media(dir.toURI().toString());
		MediaPlayer player = new MediaPlayer(media);
		players.add(player);
		player.play();
		 
		 player.setOnEndOfMedia(new Runnable () {

				@Override
				public void run() {
					
					System.out.println("main_menu.mp3 stoped playing");
					player.stop();
					player.play();
				}
				  
			  });
	}
	  
}
