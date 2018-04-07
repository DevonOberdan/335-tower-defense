package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Path {
	public Point start;
	public Point end;
	public List<Point> turns;
	
	public Path() {
		start = new Point(0, 0);
		end = new Point(500, 500);
		turns = new ArrayList<>();
		/*
		 * x 217.000000     y 76.000000
		 * x 233.000000     y 439.000000
		 */
		turns.add(0, new Point(217, 76));
		turns.add(0, new Point(233, 439));
		
	}
}
