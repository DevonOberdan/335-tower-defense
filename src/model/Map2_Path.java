package model;

import java.awt.Point;

public class Map2_Path extends Path{

	public Map2_Path() {
		super();
	}
	@Override
	public Point checkTurns(Point p, Enemy e) {
		int x=0; int y=0;
		
		
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
			if (p.x>248) // 239
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
			if (p.x<68)
				e.updateNumTurns();
			break;
		case 7:
			setL(false); setR(false);
			setU(true); setD(false);
			if (p.y<55)
				e.updateNumTurns();
			break;
		case 8:
			setL(false); setR(true);
			setU(false); setD(false);
			if (p.x>437)
				e.updateNumTurns();
			break;
		case 9:
			setL(false); setR(false);
			setU(false); setD(true);
			if (p.y>140)
				e.updateNumTurns();
			break;
		case 10:
			setL(true); setR(true);
			setU(false); setD(false);
			if (p.x<368)
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
