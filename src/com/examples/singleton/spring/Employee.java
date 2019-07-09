package com.examples.singleton.spring;

import org.springframework.stereotype.Component;

@Component
public class Employee {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
