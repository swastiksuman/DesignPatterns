package com.examples.spring.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	@Bean
	public Person personwa() {
		return new Person(1, "ABC", "EFG");
	}
}
