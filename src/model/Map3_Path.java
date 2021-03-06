package model;

import java.awt.Point;

import model.enemy.Enemy;

public class Map3_Path extends Path{

	public Map3_Path() {
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
		int x=0; int y=0;
		
		
		switch (e.getPathNum()) {
		case 0: // blue
			path1(p,e);
			break;
		case 1: // green
			path2(p,e);
			break;
		case 2: // red
			path3(p,e);
			break;
			default:
				break;
		}
		
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
	
	private void path1(Point p, Enemy e) {
		switch (e.getNumTurns()) {
		case 0:
				setL(false); setR(false);
				setU(false); setD(true);
				if (p.y>449)
					e.updateNumTurns();
			break;
		case 1:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<260)
				e.updateNumTurns();
			break;
		case 2:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<180)
				e.updateNumTurns();
			break;
		case 3:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<46)
				e.updateNumTurns();
			break;
		case 4:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<0)
				e.updateNumTurns();
			break;
			default:
				setL(false); setR(false);
				setU(false); setD(false);
				break;
		}
		
	}
	private void path2(Point p, Enemy e) {
		switch (e.getNumTurns()) {
		case 0:
				setL(false); setR(false);
				setU(false); setD(true);
				if (p.y>150)
					e.updateNumTurns();
			break;
		case 1:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<260)
				e.updateNumTurns();
			break;
		case 2:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<37)
				e.updateNumTurns();
			break;
		case 3:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<150)
				e.updateNumTurns();
			break;
		case 4:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>160)
				e.updateNumTurns();
			break;
		case 5:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<51)
				e.updateNumTurns();
			break;
		case 6:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<0)
				e.updateNumTurns();
			break;
			default:
				setL(false); setR(false);
				setU(false); setD(false);
				break;
		}
		
	}
	private void path3(Point p, Enemy e) {
		switch (e.getNumTurns()) {
		case 0:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>150)
				e.updateNumTurns();
		break;
	case 1:
		setL(false); setR(true);
		setU(false); setD(false);
		if (p.x>470)
			e.updateNumTurns();
		break;
	case 2:
		setL(false); setR(false);
		setU(false); setD(true);
		if (p.y>300)
			e.updateNumTurns();
		break;
	case 3:
		setL(true); setR(false);
		setU(false); setD(false);
		if (p.x<55)
			e.updateNumTurns();
		break;
	case 4:
		setL(false); setR(false);
		setU(false); setD(true);
		if (p.y>436)
			e.updateNumTurns();
		break;
	case 5:
		setL(false); setR(true);
		setU(false); setD(false);
		if (p.x>135)
			e.updateNumTurns();
		break;
	case 6:
		setL(false); setR(false);
		setU(true); setD(false);
		if (p.y<178)
			e.updateNumTurns();
		break;
		case 7:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<53)
				e.updateNumTurns();
			break;
		case 8:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<0)
				e.updateNumTurns();
			break;
	
			default:
				setL(false); setR(false);
				setU(false); setD(false);
				break;
		}
	}
	@Override
	public boolean canPlace(Point p) {
		// TODO Auto-generated method stub
		return true;
	}

}
