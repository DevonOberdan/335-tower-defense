package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path {
	private String currentMap;
	private boolean L, R, U, D;
	
	public Path(String mapName) {
		currentMap = mapName;
		L = false;
		R = false;
		U = false;
		D = false;
		
	}
	public Point checkTurns(Point p) {
		switch(currentMap) {
		case "Testing Map":
			return checkTurns_TestingMap(p);
		case "Ice Map":
			return checkTurns_IceMap(p);
			default:
				return p;
		}
	}
	private Point checkTurns_TestingMap(Point p) {
		int x = 0;
		int y =0;
		if (p.getX()<230 && p.getY()<50) 
    	  	x = 1;
      else if (p.getX()>=230 && p.getY()<430)
    	  	y = 1;
      else
    	  x = 1;
		return new Point(x , y);
	}
	private Point checkTurns_IceMap(Point p) {
		int x = 0;
		int y = 0;
		
		
		System.out.println(p.getX() + "   " + p.getY());
		
		if (p.getX()==375 && p.getY()==406) {
			L = true; R = false;
			U = true; D = false;
		}
		else if (p.getX()==305 && p.getY()==336) {
			L = false; R = false;
			U = true; D = false;
		}
		else if (p.getX()==305 && p.getY()==302) {
			L = false; R = true;
			U = true; D = false;
		}
		else if (p.getX()==459 && p.getY()==148) {
			L = false; R = false;
			U = true; D = false;
		}
		else if (p.getX()==459 && p.getY()==102) {
			L = true; R = false;
			U = true; D = false;
		}
		else if (p.getX()==409 && p.getY()==52) {
			L = true; R = false;
			U = false; D = false;
		}	
		else if (p.getX()==353 && p.getY()==52) {
			L = true; R = false;
			U = false; D = true;
		}	
		
		
		if (L) {
			x=-1;
		}
		else if (R){
			x=1;
		}
		
		if (D) {
			y=1;
		}
		else if (U){
			y=-1;
		}
		
		if (!U && !D && !L && !R)
			y=-1;
		
		
		return new Point(x , y);
	}
}
