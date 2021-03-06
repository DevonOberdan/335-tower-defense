package model;

import java.awt.Point;

import model.enemy.Enemy;
/*
 * This class will hold the predefined path for Map1
 */
public class Map2_Path extends Path{
	// Constructor  
	public Map2_Path() {
		// call the constructor in the abstract class
		super();
	}
	/**
	 * Point checkTurns (Point, Enemy)
	 * this method takes a point and a reference to an enemy 
	 * and return a Point that indicate the offset that should
	 * be added to the enemy's location in order to move
	 */
	@Override
	public Point checkTurns(Point p, Enemy e) {
		int x=0; int y=0;// x and y will save the new direction of movement
		
		// check how many turns did the enemy take and branch accordingly.
		// each case represents a turn on the map
	
		switch (e.getNumTurns()) {
		case 0:
				setL(false); setR(true);
				setU(false); setD(false);
				if (p.x>53)
					e.updateNumTurns();
			break;
		case 1:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<290) // 308
				e.updateNumTurns();
			break; 
		case 2:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>159)
				e.updateNumTurns();
			break;
		case 3:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>390) // 440
				e.updateNumTurns();
			break;
		case 4:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>251) // 239
				e.updateNumTurns();
			break;
		case 5:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<160)
				e.updateNumTurns();
			break;
		case 6:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<63)
				e.updateNumTurns();
			break;
		case 7:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<51)
				e.updateNumTurns();
			break;
		case 8:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>442)
				e.updateNumTurns();
			break;
		case 9:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>140)
				e.updateNumTurns();
			break;
		case 10:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<362)
				e.updateNumTurns();
			break;
		case 11:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>520)
				e.updateNumTurns();
			break;

			default:
				setL(false); setR(false);
				setU(false); setD(false);
				break;
		}
		
		// if the enemy needs to turn in any direction, set the x and y value to that 	
		if (getL()) {
			x=-1;
		}
		else if (getR()){
			x=1;
		}
		
		if (getD()) {
			y=1;
		}
		else if (getU()){
			y=-1;
		}

		// return a new point with the x and y values
		return new Point(x,y);
	}
	@Override
	public boolean canPlace(Point p) {
		// TODO Auto-generated method stub
		return true;
	}

}
