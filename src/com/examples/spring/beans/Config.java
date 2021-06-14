package com.examples.spring.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/com/examples/spring/beans/person.properties")
public class Config {
	@Value("${person.id}")
	private int personId;
	@Value("${person.firstName}")
	private String firstName;
	@Value("${person.lastName}")
	private String lastName;
	
	
	@Bean
	public Person personwa() {
		return new Person(personId, firstName, lastName);
	}
}
