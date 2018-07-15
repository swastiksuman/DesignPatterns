package com.examples.factory;

class Point {
	private double x, y;

	private Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}


	public static Point newCartesianPoint(double x, double y) {
		return new Point(x, y);
	}
	
	public static Point newPolarPoint(double rho, double theta){
		return new Point(rho*Math.cos(theta), rho*Math.sin(theta));
	}
}

public class FactoryMethod {
	public static void main(String[] args){
		System.out.println(Point.newCartesianPoint(2, 3).toString());
		System.out.println(Point.newPolarPoint(11, 20));
	}
}
