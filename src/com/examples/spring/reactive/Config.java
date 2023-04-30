package com.examples.spring.reactive;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.examples.spring.beans.Person;

import reactor.core.publisher.Flux;

@Configuration
public class Config {
	
	@Bean
	public PersonService personService() {
		return new PersonService();
	}
	
	@Bean
	public StringService stringService() {
		return new StringService();
	}
}
