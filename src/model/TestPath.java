package model;

import java.awt.Point;

public class TestPath extends Path{

	@Override
	public Point checkTurns(Point p) {
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
