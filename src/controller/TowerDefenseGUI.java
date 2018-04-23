package controller;

import java.util.Observer;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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

	
	private StackPane pane; // pane will represent the scene
	
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
		pane = new StackPane();
		Scene scene = new Scene (pane, 580,500);
		Observer welcome = new WelcomeView();
		pane.getChildren().add((Node) welcome);
		//pane.setCenter((Node) welcome);
		stage.setScene(scene);
		stage.show();
	}



}
