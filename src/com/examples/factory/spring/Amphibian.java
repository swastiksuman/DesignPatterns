package com.examples.factory.spring;

import org.springframework.stereotype.Component;

@Component
public class Amphibian implements Animal{

	@Override
	public void sleep() {
		System.out.println("Amphibian Sleeps");
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Amphibian";
	}

}
