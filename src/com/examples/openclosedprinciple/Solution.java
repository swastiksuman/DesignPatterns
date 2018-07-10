package com.examples.openclosedprinciple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Solution {
	public static void main(String[] args){
		Product apple = new Product("Apple", Size.SMALL, Color.RED);
		Product tree = new Product("Tree", Size.LARGE, Color.GREEN);
		Product house = new Product("Apple", Size.HUGE, Color.BLUE);
		
		BetterFilter bf = new BetterFilter();
		bf.filter(Arrays.asList(apple, tree, house), new ColorSpecification(Color.GREEN)).forEach(System.out::println);
		bf.filter(Arrays.asList(apple, tree, house), new AndSpecification(Arrays.asList(new ColorSpecification(Color.BLUE), new SizeSpecification(Size.HUGE)))).forEach(System.out::println);;
	}
}

interface Specification<T>{
	boolean isSatisfied(T item);
}

interface Filter<T>{
	Stream<T> filter(List<T> items, Specification<T> spec);
}

class ColorSpecification implements Specification<Product>{
	
	private Color color;
	
	ColorSpecification(Color color){
		this.color = color;
	}
	@Override
	public boolean isSatisfied(Product item) {
		return item.color == color;
	}
	
}

class SizeSpecification implements Specification<Product>{
	
	private Size size;
	
	SizeSpecification(Size size){
		this.size = size;
	}
	@Override
	public boolean isSatisfied(Product item) {
		return item.size == size;
	}
	
}

class AndSpecification implements Specification<Product>{
	
	private List<Specification> specifications;
	
	AndSpecification(List<Specification> specifications){
		this.specifications = specifications;
	}
	
	@Override
	public boolean isSatisfied(Product item) {
		for(Specification specification: specifications){
			if(!specification.isSatisfied(item)){
				return false;
			}
		}
		return true;
	}
	
}

class BetterFilter implements Filter<Product>{

	@Override
	public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
		return items.stream().filter(product -> spec.isSatisfied(product));
	}
	
}