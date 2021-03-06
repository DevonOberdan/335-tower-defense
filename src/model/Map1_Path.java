package model;

import java.awt.Point;

import model.enemy.Enemy;
/*
 * This class will hold the predefined path for Map1
 */
public class Map1_Path extends Path{

	// Constructor  
	public Map1_Path() {
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
		int x=0; int y=0; // x and y will save the new direction of movement
		
		// check how many turns did the enemy take and branch accordingly.
		// each case represents a turn on the map
		switch (e.getNumTurns()) {
		case 0: 
				setL(false); setR(true);
				setU(false); setD(false);
				if (p.x>109)
					e.updateNumTurns();
			break;
		case 1:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>114)
				e.updateNumTurns();
			break;
		case 2:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>219)
				e.updateNumTurns();
			break;
		case 3:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<44)
				e.updateNumTurns();
			break;
		case 4:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>336)
				e.updateNumTurns();
			break;
		case 5:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>210)
				e.updateNumTurns();
			break;
		case 6:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<231)
				e.updateNumTurns();
			break;
		case 7:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>400)
				e.updateNumTurns();
			break;
		case 8:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>341)
				e.updateNumTurns();
			break;
		case 9:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<328)
				e.updateNumTurns();
			break;
		case 10:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>454)
				e.updateNumTurns();
			break;
		case 11:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>403)
				e.updateNumTurns();
			break;
		case 12:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.y>500)
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
		int x=(int) p.getX(), y=(int) p.getY();
		
		if (y>=33 && y<=70)
			if (x>=0 && x<=126)
				return false;
		
		if (y>=70 && y<=155)
			if (x>=90 && x<=126)
				return false;
		
		if (y>=116 && y<=158)
			if (x>=126 && x<=236)
				return false;

		
		
		return true;
	}

}
