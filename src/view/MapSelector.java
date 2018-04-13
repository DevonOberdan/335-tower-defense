package view;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Map;
import model.TestMap;


public class MapSelector extends BorderPane implements Observer {
	
	private BorderPane pane;
	private Map theGame;
	private Observer gameView, currentView;
	
	public MapSelector() {
		theGame = null;
		pane = new BorderPane();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		//pane.setPadding(new Insets (10,10,10,10));
		
		Label copyright = new Label ("  copyright The Team\n");
		Button easy = new Button("Easy");
		easy.setMinWidth(120);
		Button meduim = new Button("Medium");
		meduim.setMinWidth(120);
		Button hard = new Button("Hard");
		hard.setMinWidth(120);
		//gameView = new GameView(theGame);

		grid.add(easy, 11, 18);
		grid.add(meduim, 11, 19);
		grid.add(hard, 11, 20);
		grid.add(copyright, 0, 39);
		
		MapHandler handler = new MapHandler();
		easy.setOnAction(handler);
		meduim.setOnAction(handler);
		hard.setOnAction(handler);

		
		
		this.setCenter(grid);
		this.setVisible(true);
	}
	public void setViewTo(Observer newView) {
		this.setCenter(null);
		currentView = newView;
		this.setCenter((Node) currentView); 
	}
	private class MapHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			Button selected = (Button) event.getSource();
			switch (selected.getText().toLowerCase()) {
			case "medium":
				gameView = new GameView("Ice Map");
				setViewTo(gameView);
				((GameView) gameView).show();
				break;
			case "hard":
				gameView = new GameView("Testing Map");
				setViewTo(gameView);
				((GameView) gameView).show();
				break;
				
				default: // easy mode
					gameView = new GameView("Testing Map");
					setViewTo(gameView);
					((GameView) gameView).show();
					break;
			}
			
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
