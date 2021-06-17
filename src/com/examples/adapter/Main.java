package com.examples.adapter;

public class Main {
	
	/*
	 * An Adapter pattern acts as a connector between two incompatible 
	 * interfaces that otherwise cannot be connected directly.
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Movable bugattiVeyron = new BugattiVeyron();
	    MovableAdapter bugattiVeyronAdapter = new MovableAdapterImpl(bugattiVeyron);
	    System.out.println(bugattiVeyron.getSpeed());
	    System.out.println(bugattiVeyronAdapter.getSpeed());
	}

}
