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

public class TowerDefenseGUI extends Application{

	
	private Observer currentView; // currentView will hold the current view of the observed
	private BorderPane pane; // pane will represent the scene
	private Observer gameView; // game view of the map
	private Map theGame = new Map();
	public static void main (String [] args)
	{
		launch(args);
	}
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
		
		grid.add(newGame, 11, 18);
		grid.add(loadGame, 11, 19);
		grid.add(instructions, 11, 20);
		grid.add(copyright, 0, 39);
		newGame.setOnAction(e -> {
			setViewTo(gameView);
			System.out.println("Game View");
		});
		
		
		gameView = new GameView(theGame);
		
		//pane.set
		pane.setCenter(grid);
		stage.setScene(scene);
		stage.show();
	}

	/*
	   * void setViewTo(Observer)
	   * sets the current view to be the input observer  
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
