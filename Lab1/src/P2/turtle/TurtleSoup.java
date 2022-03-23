/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
//        throw new RuntimeException("implement me!");
        for(int i=0; i<4; i++) {
        	turtle.forward(sideLength);
        turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
//        throw new RuntimeException("implement me!");
    	return 180-(double)360/sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
//        throw new RuntimeException("implement me!");
    	double target = 360/(180-angle);
    	int side=3;
    	while(true) {
    		if(Math.abs(side-target)<1e-2) {
    			return side;
    		}
    		side++;
    	}
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
//        throw new RuntimeException("implement me!");
    	for(int i=0; i<sides; i++) {
    		turtle.forward(sideLength);
    		turtle.turn(180-calculateRegularPolygonAngle(sides));
    	}
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
//        throw new RuntimeException("implement me!");
    	
    	double alpha;
    	if(currentY == targetY) {
    		if(targetX >= currentX) {
    			alpha = 90;
    		}else{
    			alpha = -90;
    		}
    	}else {
    		alpha = Math.atan((double)(targetX-currentX)/(targetY-currentY))*180/Math.PI;
    	}
    	if(targetY < currentY) alpha += 180.0;
    	double ans = alpha-currentBearing;

    	if(ans < 0) {
    		ans += 360;
    	}else if(ans > 360) {
    		ans -= 360;
    	}
    
    	return ans;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
//        throw new RuntimeException("implement me!");
    	List<Double> angles = new ArrayList<Double>();
    	
    	double currentBearing = 0;
    	
    	for (int i=0; i<xCoords.size()-1; i++) {
    		angles.add(calculateBearingToPoint(currentBearing, xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1)));
    		currentBearing = Math.atan((double)(xCoords.get(i+1)-xCoords.get(i)/(yCoords.get(i+1)-yCoords.get(i))))*180/Math.PI;
    		if(yCoords.get(i+1)<yCoords.get(i)) currentBearing += 180;
    	}
    	
    	return angles;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
//        throw new RuntimeException("implement me!");
    	if(points.size()<=2) return points;
    	
    	Set<Point> ans = new HashSet<Point>();
    	
    	Point[] p = points.toArray(new Point[points.size()]);
    	boolean[] used = new boolean[points.size()];
    	
    	Point left = new Point(p[0].x(),p[0].y());
    	int index = 0;
    	
    	for(int i=1;i<p.length;i++) {
    		if(p[i].x()<left.x()) {
    			left = p[i];
    			index = i;
    		}
    	}
    	
    	ans.add(p[index]);
    	used[index] = true;
    	Point current = new Point(left.x(),left.y());
    	double currentBearing = 0, tempBearing, tempDistance;
    	
    	for(int i=0; i<p.length; i++) {
    		double angle = 360, distance = 1000;
    		int k = 0;
    		for(int j=0; j<p.length; j++) {
    			if(Math.abs(current.x()-p[j].x())<1e-6 && Math.abs(current.y()-p[j].y())<1e-6) {
    				continue;
    			}
    			tempBearing = calculateBearingToPoint(currentBearing, (int)current.x(), (int)current.y(), (int)p[j].x(), (int)p[j].y());
    			tempDistance = Math.sqrt(Math.pow(p[j].x()-current.x(),2)+Math.pow(p[j].y()-current.y(),2));
    			if(tempBearing < angle) {
    				angle = tempBearing;
    				k = j;
    				distance = tempDistance;
    			}else if(tempBearing == angle && tempDistance > distance) {
    				distance = tempDistance;
    				k = j;
    			}
    		}
    		if(!used[k]) {
    			used[k] = true;
    		    ans.add(p[k]);
    		}else {
    			return ans;
    		}
    		
    		if(Math.abs(current.y()-p[k].y())<1e-6) {
    			if(p[k].x()>current.x()) {
    				currentBearing = 90;
    			}else {
    				currentBearing = -90;
    			}
    		}else {
    			currentBearing = Math.atan((current.x()-p[k].x())/(current.y()-p[k].y()))*180/Math.PI;
    		}
    		if(p[k].y()<current.y()) {
    			currentBearing += 180;
    		}
    		current = p[k];
    	}
    	
    	return ans;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
//        throw new RuntimeException("implement me!");
    	turtle.color(PenColor.BLUE);
    	
    	int sides = 9, sideLength = 100;
    	
    	for(int i=0; i<sides; i++) {
    		turtle.forward(sideLength);
    		turtle.turn(180-calculateRegularStarAngle(sides));
    	}
    	
    }
    
    public static double calculateRegularStarAngle(int sides) {
    	if(sides % 2 == 1 && sides >= 5) {
    		return (double)180/sides;
    	}else if(sides % 2 == 0 && sides >= 8) {
    		return (double)360/sides;
    	}else {
    		System.out.println("不存在当前角数的正多角星");
    		return -1;
    	}
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

//        drawSquare(turtle, 40);
        
//        drawRegularPolygon(turtle, 6, 40);
        
        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
