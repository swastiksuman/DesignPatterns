package com.examples.observer;

import java.util.ArrayList;

public class StockGrabber implements Subject {

	private ArrayList<Observer> observers;
	private double ibmPrice;
	private double aaplPrice;
	private double googPrice;

	public StockGrabber() {
		observers = new ArrayList<Observer>();
	}

	@Override
	public void register(Observer o) {
		observers.add(o);
	}

	@Override
	public void unregister(Observer o) {
		observers.remove(observers.indexOf(o));
	}

	@Override
	public void notifyObserver() {
		observers.stream().forEach((observer) -> observer.update(ibmPrice, aaplPrice, googPrice));
	}

	public void setIbmPrice(double newIbmPrice) {
		this.ibmPrice = newIbmPrice;
		notifyObserver();
	}

	public void setAaplPrice(double newAaplPrice) {
		this.aaplPrice = newAaplPrice;
		notifyObserver();
	}

	public void setGoogPrice(double newGoogPrice) {
		this.googPrice = newGoogPrice;
		notifyObserver();
	}
}
