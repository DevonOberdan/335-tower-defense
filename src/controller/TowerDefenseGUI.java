package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TowerDefenseGUI extends Application{

	public static void main (String [] args)
	{
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Tower Defense");
		BorderPane window = new BorderPane();
		Scene scene = new Scene (window, 500,500);
		GridPane pane = new GridPane();
		pane.setVgap(10);
		pane.setHgap(10);
		//pane.setPadding(new Insets (10,10,10,10));
		
		Label copyright = new Label ("  copyright The Team\n");
		Button newGame = new Button("New Game");
		newGame.setMinWidth(120);
		Button loadGame = new Button("Load Game");
		loadGame.setMinWidth(120);
		Button instructions = new Button("Instructions");
		instructions.setMinWidth(120);
		
		pane.add(newGame, 7, 17);
		pane.add(loadGame, 7, 18);
		pane.add(instructions, 7, 19);
		pane.add(copyright, 0, 39);
		//pane.set
		window.setCenter(pane);
		stage.setScene(scene);
		stage.show();
	}

}
