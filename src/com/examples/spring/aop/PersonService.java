package com.examples.spring.aop;

import org.springframework.stereotype.Component;

@Component
public class PersonService {
	public Person getPerson() {
		return new Person(1, "Soujanya", "Talla");
	}
}
