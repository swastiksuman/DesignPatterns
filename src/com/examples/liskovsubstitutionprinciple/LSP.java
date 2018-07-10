package com.examples.liskovsubstitutionprinciple;

/*As per the LSP, functions that use references to base classes must be able to
 *  use objects of the derived class without knowing it. 
 * In simple words, derived classes must be substitutable for the base class.*/

public class LSP {
	
	static void userIt(Rectangle r){
		int width = r.getWidth();
		r.setHeight(10);
		System.out.println("Expected: " + (width*10) + " Got: "+r.getArea());
	}
	
	public static void main(String[] args) {
		Rectangle rc = new Rectangle(2, 3);
		userIt(rc);
		
		Rectangle sq = new Square();
		sq.setHeight(5);
		userIt(sq);
	}

}

class Rectangle{
	protected int width, height;
	
	public Rectangle(){
		
	}

	public Rectangle(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getArea(){ return width * height; }
	@Override
	public String toString() {
		return "Rectangle [width=" + width + ", height=" + height + "]";
	}
}

class Square extends Rectangle{
	public Square() {}
	
	public Square(int size){
		width = height = size;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		super.setWidth(width);
		super.setHeight(width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		super.setHeight(height);
		super.setWidth(height);
	}
}

