package model;

import java.awt.Point;

import model.enemy.Enemy;

public class IcePath extends Path {

	@Override
	public Point checkTurns(Point p, Enemy e) {
		int x = 0;
		int y = 0;
		
		
		System.out.println(p.getX() + "   " + p.getY());
		
		if ((p.getX()==375 && p.getY()==406) || (p.getX()==459 && p.getY()==102)) {
			setL(true); setR(false);
			setU(true); setD(false);
		}
		else if ((p.getX()==305 && p.getY()==336) || (p.getX()==459 && p.getY()==148)) {
			setL(false); setR(false);
			setU(true); setD(false);
		}
		else if (p.getX()==305 && p.getY()==302) {
			setL(false); setR(true);
			setU(true); setD(false);
		}
		else if (p.getX()==409 && p.getY()==52) {
			setL(true); setR(false);
			setU(false); setD(false);
		}	
		else if (p.getX()==353 && p.getY()==52) {
			setL(true); setR(false);
			setU(false); setD(true);
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
		
		if (!getU() && !getD() && !getL() && !getR())
			y=-1;
		
		
		return new Point(x , y);
	}

}
