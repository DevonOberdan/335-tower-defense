package view;

import java.util.Observer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.layout.BorderPane;
import model.Map;

public class InstructionView extends BorderPane implements Observer{
	private static final String instructions = "Welcome to Tower Defense! This game is brought"
			+"to you by The Team.\nTo play the game, simply click \"New Game\", or if you'd like to load a previously\n"
			+"saved game, go ahead and click \"Load Game\". If at any time you would like to rage-quit and exit the game, just\n"
			+"hit the 'x' in the top right corner of your window and it'll close the\ngame tab.\nOnce you've begun your defense, please"
			+" click and drag a tower to the location\nyou see best fit the map circumstances.";
	public InstructionView(Map map) {
		
	}

	@Override
	public void update(java.util.Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
