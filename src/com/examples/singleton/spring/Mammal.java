package com.examples.singleton.spring;

import org.springframework.stereotype.Component;

@Component
public class Mammal implements Animal{

	@Override
	public void sleep() {
		System.out.println("Mammal Sleeps");
	}

	@Override
	public String getType() {
		return "Mammal";
	}

}
