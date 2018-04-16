package model;

import java.awt.Point;

public class TestPath extends Path{

	public TestPath() {
		super();
	}
	@Override
	public Point checkTurns(Point p) {
		int x=0; int y=0;
		
		if (p.getX()>=230 && p.getY()<430) {
			setL(false); setR(false);
			setU(false); setD(true);
		}
		else {
			setL(false); setR(true);
			setU(false); setD(false);
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
		
		//if (!getU() && !getD() && !getL() && !getR())
		//	y=-1;
		
		return new Point(x,y);
	}

}
