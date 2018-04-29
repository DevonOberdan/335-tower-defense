package view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
/**
 * Main menu! This view is for welcoming our players to a bunch of options
 * that they can choose to get the game going. Casual is where you can pick from the three maps, easy medium and hard.
 * campaign is a story-line, iterating through each map and progressing to the end.
 * @author Taite Nazifi
 *
 */
public class WelcomeView extends StackPane implements Observer{
	private Observer gameView, instructionView, currentView, selectorView;
	private boolean persistence;
	public WelcomeView(boolean persistence) {
		this.persistence = persistence;
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		//pane.setPadding(new Insets (10,10,10,10));
		//gc.drawImage(background, 0, 0);
		Label copyright = new Label ("  copyright The Team\n");
		Button newGame = new Button("Casual");
		newGame.setMinWidth(120);
		Button loadGame = new Button("Load Game");
		loadGame.setMinWidth(120);
		Button instructions = new Button("Instructions");
		instructions.setMinWidth(120);
		Button campaign = new Button("Campaign");
		campaign.setMinWidth(120);
		grid.add(campaign, 11, 18);
		grid.add(newGame, 11, 19);
		grid.add(loadGame, 11, 20);
		grid.add(instructions, 11, 21);
		grid.add(copyright, 0, 39);
		campaign.setOnAction(e -> {
			this.getStylesheets().clear();
		    this.getChildren().clear();
		    campaign.setOnAction(null);
		    instructions.setOnAction(null);
		    newGame.setOnAction(null);
			gameView = new GameView(this.persistence);
			setViewTo(gameView);
			((GameView) gameView).show();
			System.out.println("Game View"); 
		});
		newGame.setOnAction(e -> {
			this.getStylesheets().clear();
		    this.getChildren().clear();
		    campaign.setOnAction(null);
		    instructions.setOnAction(null);
		    //this.setCenter(null); // set the center of pane to null
		    this.getChildren().add((Node) new SelectorView());
			//((SelectorView)selectorView).show();
		    newGame.setOnAction(null);
			System.out.println("Map selector");
		}); 
		instructions.setOnAction(e -> {
			instructionView = new InstructionView(this.persistence);
			setViewTo(instructionView);
			System.out.println("Instruction View");
		});
		loadGame.setOnAction(e -> {
			this.getStylesheets().clear();
		    this.getChildren().clear();
		    campaign.setOnAction(null);
		    instructions.setOnAction(null);
		    newGame.setOnAction(null);
		    this.persistence = true;
			gameView = new GameView(this.persistence);
			setViewTo(gameView);
			((GameView) gameView).show();
			System.out.println("Game View"); 
		});
		this.getChildren().add(grid);
		//this.setCenter(grid);
		this.setId("pane");
		this.getStylesheets().addAll(this.getClass().getResource("welcomView_style.css").toExternalForm());
		this.setVisible(true);
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
	  
}
