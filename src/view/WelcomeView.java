package view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Map;
import model.TestMap;

public class WelcomeView extends BorderPane implements Observer{
	private BorderPane pane;
	private Map theGame;
	private Observer gameView, instructionView, currentView, mapSelector;
	
	public WelcomeView() {
		theGame = new TestMap(null);
		pane = new BorderPane();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		//pane.setPadding(new Insets (10,10,10,10));
		
		Label copyright = new Label ("  copyright The Team\n");
		Button newGame = new Button("New Game");
		newGame.setMinWidth(120);
		Button loadGame = new Button("Load Game");
		loadGame.setMinWidth(120);
		Button instructions = new Button("Instructions");
		instructions.setMinWidth(120);
		//gameView = new GameView(theGame);
		instructionView = new InstructionView();
		//mapSelector = new MapSelector();
		grid.add(newGame, 11, 18);
		grid.add(loadGame, 11, 19);
		grid.add(instructions, 11, 20);
		grid.add(copyright, 0, 39);
		newGame.setOnAction(e -> {
			gameView = new GameView();
			setViewTo(gameView);
			((GameView) gameView).show();
			System.out.println("Game View"); 
		});
		instructions.setOnAction(e -> {
			setViewTo(instructionView);
			System.out.println("Instruction View");
		});
		this.setCenter(grid);
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
		    this.setCenter(null); // set the center of pane to null
		    currentView = newView; // update the current view to the input observer
		    this.setCenter((Node) currentView); // set the center of the pane to the current observer
	  }

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	  
}
