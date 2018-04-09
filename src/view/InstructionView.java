package view;

import java.util.Observer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import model.Map;

public class InstructionView extends BorderPane implements Observer{
	private static final String instructions = "Welcome to Tower Defense! This game is brought"
			+" to you by The Team.\nTo play the game, simply click \"New Game\", or if you'd like to load a previously\n"
			+"saved game, go ahead and click \"Load Game\". If at any time you would like to rage-quit\n and exit the game, just "
			+"hit the 'x' in the top right corner of your window and it'll close the game tab.\nOnce you've begun your defense, please"
			+" click and drag a tower to the location\nyou see best fit the map circumstances. Once you are done placing your towers, the round will begin.\n"
			+ "Once the round begins, fight off the enemies! Once an enemy reaches\nyour base, you will take a certain amount of damage and eventually die, causing you to\nlose the game. If you defeat all of the enemies within each wave, you will win!\n\nHappy defending.\n\n - The Team";
	public InstructionView() {
		TextArea text = new TextArea();
		text.setText(instructions);
		this.setCenter(text);
		Button butt = new Button("Back");
		butt.setMinHeight(40);
		butt.setMinWidth(580);
		butt.setOnAction(e -> {
			this.setBottom(null);
			this.setCenter(new WelcomeView());
		});
		BorderPane pane2 = new BorderPane();
		pane2.setCenter(butt);
		this.setBottom(pane2);
		this.setVisible(true);
		
	}

	@Override
	public void update(java.util.Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
