package com.examples.openclosedprinciple;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Problem {

	public static void main(String[] args) {
		Product apple = new Product("Apple", Size.SMALL, Color.RED);
		Product tree = new Product("Tree", Size.LARGE, Color.GREEN);
		Product house = new Product("Apple", Size.HUGE, Color.BLUE);
		
		ProductFilter filter = new ProductFilter();
		filter.filterByColor(Arrays.asList(apple, tree, house), Color.GREEN).forEach(System.out::println);
	}

}

enum Size {
	SMALL, MEDIUM, LARGE, HUGE
}

enum Color {
	RED, GREEN, BLUE
}

class Product{
	public String name;
	public Size size;
	public Color color;
	public Product(String name, Size size, Color color) {
		super();
		this.name = name;
		this.size = size;
		this.color = color;
	}
	@Override
	public String toString() {
		return "Product [name=" + name + ", size=" + size + ", color=" + color + "]";
	}
}

class ProductFilter{
	public Stream<Product> filterByColor(List<Product> products, Color color){
		return products.stream().filter(product -> product.color == color);
	}
	
	public Stream<Product> filterBySize(List<Product> products, Size size){
		return products.stream().filter(product -> product.size == size);
	}
	
	public Stream<Product> filterByColorSize(List<Product> products, Color color, Size size){
		return products.stream().filter(product -> product.size == size && product.color == color);
	}
}