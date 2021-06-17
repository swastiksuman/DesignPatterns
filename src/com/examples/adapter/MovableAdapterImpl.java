package com.examples.adapter;

public class MovableAdapterImpl implements MovableAdapter {
	private Movable luxuryCars;

	// standard constructors

	@Override
	public double getSpeed() {
		return convertMPHtoKMPH(luxuryCars.getSpeed());
	}

	public MovableAdapterImpl(Movable luxuryCars) {
		super();
		this.luxuryCars = luxuryCars;
	}

	private double convertMPHtoKMPH(double mph) {
		return mph * 1.60934;
	}
}