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
	private final static String persistedFileName = "listOfTowers";
	
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
		
		initializeTowers(); 
		
		Observer welcome = new WelcomeView();
		pane.getChildren().add((Node) welcome);
		//pane.setCenter((Node) welcome);
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(new WritePersistentObjectOrNot());
	}
	
	private void initializeTowers() {
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Start up Option");
	    alert.setHeaderText("Press ok to read persistent object(s)");
	    alert.setContentText("Press cancel while system testing.");
	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.get() == ButtonType.OK) {
	      readPersistentListOfStrings();
	    } else {
	     // setupDefaultList();
	    }
	  }
	
	  private void readPersistentListOfStrings() {
		    try {
		      FileInputStream fileOutput = new FileInputStream(persistedFileName);
		      ObjectInputStream in = new ObjectInputStream(fileOutput);
		      // TODO 9: Read the object from a disk file, assuming it is present
		      // FXCollections.observableList(List<E>) is not Serializable
		      // so we have to store all elements into a List of some sort
		      @SuppressWarnings("unchecked")
		      ArrayList<String> list = (ArrayList<String>) in.readObject();
		      for (String name : list) {
		    	  //observableList.add(name);
		      }
		      in.close();
		    
		    
		    
		    
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	  private class WritePersistentObjectOrNot implements EventHandler<WindowEvent> {

		    @Override
		    public void handle(WindowEvent event) {
		      Alert alert = new Alert(AlertType.CONFIRMATION);
		      alert.setTitle("Shut Down Option");
		      alert.setHeaderText("Press ok to write persistent object(s)");
		      alert.setContentText("Press cancel while system testing.");
		      Optional<ButtonType> result = alert.showAndWait();

		      if (result.get() == ButtonType.OK) {
		        writePersistentListOfStrings();
		      }
		    }
		  }


	  public void writePersistentListOfStrings() {
		    try {
		      FileOutputStream fileOutput = new FileOutputStream(persistedFileName);
		      ObjectOutputStream out = new ObjectOutputStream(fileOutput);
		      // FXCollections.observableList(List<E>) is not Serializable
		      // so we have to store all elements into a List of some sort
		      
		      // TODO 8: Write the list of string to a disk file
		      ArrayList<String> list = new ArrayList<String>();
		     /* for (String name : observableList) {
		    	  list.add(name);
		      }*/
		      out.writeObject(list);
		      out.close();
		    
		    
		    
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }


}
