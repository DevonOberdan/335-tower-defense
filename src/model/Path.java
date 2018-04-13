package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path {
	private String currentMap;
	
	public Path(String mapName) {
		currentMap = mapName;

		
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
		int y =0;
		if (p.getX()<230 && p.getY()<50) 
    	  	x = 1;
      else if (p.getX()>=230 && p.getY()<430)
    	  	y = 1;
      else
    	  x = 1;
		return new Point(x , y);
	}
}
