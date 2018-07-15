package com.examples.factory.classes;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

public class AbstractFactoryClass {

	public static void main(String[] args) {

	}

}

interface HotDrink{
	void consume();
}

class Tea implements HotDrink{

	@Override
	public void consume() {
		System.out.println("Delicious Tea");
	}
	
}

class Coffee implements HotDrink{

	@Override
	public void consume() {
		System.out.println("Delicious Coffee");
	}
}

interface HotDrinkFactory{
	HotDrink prepare(int amount);
}

class TeaFactory implements HotDrinkFactory{

	@Override
	public HotDrink prepare(int amount) {
		System.out.println("Do all tea tasks");
		return new Tea();
	}
}

class CoffeeFactory implements HotDrinkFactory{

	@Override
	public HotDrink prepare(int amount) {
		System.out.println("Do all Coffee tasks");
		return new Coffee();
	}
}

class HotDrinkMachine{
	private List<Pair<String, HotDrinkFactory>> namedFactories = new ArrayList<>();
	
	public HotDrinkMachine(){
		
	}
}
