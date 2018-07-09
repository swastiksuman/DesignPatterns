package com.examples.observer;

import java.text.DecimalFormat;

public class GetTheStock implements Runnable{
	
	private int startTime;
	private String stock;
	private Double price;
	
	private Subject stockGrabber;

	public GetTheStock(int startTime, String stock, Double price, Subject stockGrabber) {
		super();
		this.startTime = startTime;
		this.stock = stock;
		this.price = price;
		this.stockGrabber = stockGrabber;
	}

	@Override
	public void run() {
		for(int i=0; i<20;i++){
			try{
				Thread.sleep(2000);
			}catch(InterruptedException e){}
			double randNum = (Math.random()*(0.6)) -0.3;
			DecimalFormat df = new DecimalFormat("#.##");
			price = Double.valueOf(df.format(price+randNum));
			if(stock == "IBM"){
				((StockGrabber)stockGrabber).setIbmPrice(price);
			}
			if(stock == "AAPL"){
				((StockGrabber)stockGrabber).setAaplPrice(price);
			}
			if(stock == "GOOG"){
				((StockGrabber)stockGrabber).setGoogPrice(price);
			}
		}
	}
	
	
}
