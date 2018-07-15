package com.examples.factory.classes;


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

	public static class Factory {
		public static Point newCartesianPoint(double x, double y) {
			return new Point(x, y);
		}

		public static Point newPolarPoint(double rho, double theta) {
			return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
		}
	}
}

public class FactoryClass {
	public static void main(String[] args) {
		System.out.println(Point.Factory.newCartesianPoint(2, 3).toString());
		System.out.println(Point.Factory.newPolarPoint(11, 20));
	}
}
