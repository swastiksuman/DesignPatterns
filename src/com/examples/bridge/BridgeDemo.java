package com.examples.bridge;

public class BridgeDemo {
	public static void main(String[] args) {
		RasterRenderer raster = new RasterRenderer();
		VectorRenderer vector = new VectorRenderer();
		Circle circle = new Circle(vector, 5);
		circle.draw();
		circle.resize(2);
		circle.draw();
	}
}

interface Renderer {
	void renderCircle(float radius);
}

class VectorRenderer implements Renderer {

	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing circle of radius 5");
	}

}

class RasterRenderer implements Renderer {

	@Override
	public void renderCircle(float radius) {
		System.out.println("Drawing pixels of radius 5 ");
	}

}

abstract class Shape {
	Renderer renderer;

	public Shape(Renderer renderer) {
		this.renderer = renderer;
	}

	public abstract void draw();

	public abstract void resize(float factor);
}

class Circle extends Shape {

	public float radius;

	public Circle(Renderer renderer, float radius) {
		super(renderer);
		this.radius = radius;
	}

	@Override
	public void draw() {
		System.out.println("Drawing Circle with radius " + radius);
	}

	@Override
	public void resize(float factor) {
		radius *= radius;
		System.out.println("New radius " + radius);
	}
}