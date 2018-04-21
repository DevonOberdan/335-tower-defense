package model;

import java.awt.Point;

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
				if (p.x>105)
					e.updateNumTurns();
			break;
		case 1:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>126)
				e.updateNumTurns();
			break;
		case 2:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>213)
				e.updateNumTurns();
			break;
		case 3:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<60)
				e.updateNumTurns();
			break;
		case 4:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>334)
				e.updateNumTurns();
			break;
		case 5:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>228)
				e.updateNumTurns();
			break;
		case 6:
			setL(true); setR(false);
			setU(false); setD(false);
			if (p.x<230)
				e.updateNumTurns();
			break;
		case 7:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>410)
				e.updateNumTurns();
			break;
		case 8:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>332)
				e.updateNumTurns();
			break;
		case 9:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<342)
				e.updateNumTurns();
			break;
		case 10:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>448)
				e.updateNumTurns();
			break;
		case 11:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>416)
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
