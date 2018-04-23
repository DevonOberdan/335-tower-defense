package model;

import java.awt.Point;

import model.enemy.Enemy;

public class Map1_Path extends Path{

	public Map1_Path() {
		super();
	}
	@Override
	public Point checkTurns(Point p, Enemy e) {
		int x=0; int y=0;
		
		
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

		
		return new Point(x,y);
	}

}
