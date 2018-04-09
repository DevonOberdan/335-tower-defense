package controller;

import java.util.Observer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Map;
import view.GameView;
import view.InstructionView;
import view.WelcomeView;

/**
 * TowerDefenseGUI is the heart of the tower defense application.
 * This class will initialize and create the pane in which we are able
 * to interact with the various buttons and features. Let's us
 * swap between views by clicking on the various buttons. Starts up
 * the program when running this class.
 * 
 * MAIN
 *
 */
public class TowerDefenseGUI extends Application{

	
	private Observer currentView; // currentView will hold the current view of the observed
	private BorderPane pane; // pane will represent the scene
	private Observer gameView, instructionView; // observable views
	private Map theGame = new Map();
	
	
	public static void main (String [] args)
	{
		launch(args);
	}
	
	/**
	 * Creates the welcome GUI and initializes each button on the welcome
	 * pane. throws Exception, and overrides Application's start method.
	 * @param stage the stage in which we dance upon.
	 * @author The Team
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Tower Defense");
		pane = new BorderPane();
		Scene scene = new Scene (pane, 580,500);
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
		gameView = new GameView(theGame);
		instructionView = new InstructionView();
		grid.add(newGame, 11, 18);
		grid.add(loadGame, 11, 19);
		grid.add(instructions, 11, 20);
		grid.add(copyright, 0, 39);
		newGame.setOnAction(e -> {
			setViewTo(gameView);
			System.out.println("Game View");
		});
		instructions.setOnAction(e -> {
			setViewTo(instructionView);
			System.out.println("Instruction View");
		});
		//pane.set
		Observer welcome = new WelcomeView();
		pane.setCenter((Node) welcome);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * setViewTo sets the current view of the application to newView,
	 * allowing us to swap between views willy-nilly.
	 * 
	 * @param newView the view we want to change to.
	 * @author The Team
	 */
	  private void setViewTo(Observer newView) {
		    pane.setCenter(null); // set the center of pane to null
		    currentView = newView; // update the current view to the input observer
		    pane.setCenter((Node) currentView); // set the center of the pane to the current observer
		    pane.setOnMouseClicked(e -> {
				System.out.printf("x %f     y %f\n", e.getX(), e.getY());
		    });
	  }


}
