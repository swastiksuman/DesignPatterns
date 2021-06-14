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
	public Flux<List<Person>> listPerson() {
		List<Person> listOfPersons = new ArrayList();
		listOfPersons.add(new Person(1, "ABC", "DEF"));
		listOfPersons.add(new Person(2, "GHI", "JKL"));
		Flux listPersons = Flux.just(listOfPersons);
		return listPersons;
	}
}
