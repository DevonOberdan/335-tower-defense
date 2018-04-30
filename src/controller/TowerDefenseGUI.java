package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.IntroView;
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
		//Observer welcome = new WelcomeView();
		Observer intro = new IntroView(this);
		pane.getChildren().add((Node) intro);
		((IntroView) intro).play();
		//pane.setCenter((Node) welcome);
		stage.setScene(scene);
		stage.show();
	}
	public void next() {
		Observer welcome = new WelcomeView();
		pane.getChildren().remove(0);
		pane.getChildren().add((Node) welcome);
	}
	
	

}
