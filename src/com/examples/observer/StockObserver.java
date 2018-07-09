package com.examples.observer;

public class StockObserver implements Observer {

	private double ibmPrice;
	private double aaplPrice;
	private double googPrice;

	private static int observerIDTracker = 0;

	private int observerID;
	private Subject stockGrabber;

	public StockObserver(Subject stockGrabber) {
		this.stockGrabber = stockGrabber;
		this.observerID = ++observerIDTracker;
		System.out.println("New Observer " + this.observerID);
		stockGrabber.register(this);
	}

	@Override
	public void update(double ibmPrice, double aaplPrice, double googprice) {
		this.ibmPrice = ibmPrice;
		this.aaplPrice = aaplPrice;
		this.googPrice = googprice;
		printPrices();
		
	}
	
	public void printPrices(){
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "StockObserver [ibmPrice=" + ibmPrice + ", aaplPrice=" + aaplPrice + ", googPrice=" + googPrice + "]";
	}
	

}
