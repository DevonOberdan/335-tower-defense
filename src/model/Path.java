package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path {
	private Point start;
	private Point end;
	private List<Point> turns;
	private String currentMap;
	
	public Path(String mapName) {
		currentMap = mapName;
		start = new Point(0, 0);
		end = new Point(500, 500);
		turns = new ArrayList<>();
		/*
		 * x 217.000000     y 76.000000
		 * x 233.000000     y 439.000000
		 */
		turns.add(new Point(217, 76));
		turns.add(new Point(233, 439));
		
	}
	public List<Point> getTurn() {
		return turns;
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
