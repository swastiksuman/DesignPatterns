package com.examples.observer;

public class Main {

	
	public static void main(String[] args) {
		StockGrabber stockGrabber = new StockGrabber();
		StockObserver stockObserver = new StockObserver(stockGrabber);

		stockGrabber.setAaplPrice(12.12);
		stockGrabber.setIbmPrice(12.12);
		stockGrabber.setGoogPrice(12.12);
	
		StockObserver stockObserver2 = new StockObserver(stockGrabber);

		stockGrabber.setAaplPrice(12.13);
		stockGrabber.setIbmPrice(12.15);
		stockGrabber.setGoogPrice(12.11);
		
		stockGrabber.unregister(stockObserver);
		
		stockGrabber.setAaplPrice(12.14);
		stockGrabber.setIbmPrice(12.12);
		stockGrabber.setGoogPrice(12.10);
		
		
		Runnable getIBM = new GetTheStock(2, "IBM", 197.00, stockGrabber);
		Runnable getAAPL = new GetTheStock(2, "AAPL", 245.00, stockGrabber);
		Runnable getGOOG = new GetTheStock(2, "GOOG", 123.00, stockGrabber);
		
		new Thread(getIBM).start();
		new Thread(getAAPL).start();
		new Thread(getGOOG).start();
	}

}
