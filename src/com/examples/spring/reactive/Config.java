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
		System.out.println("listPerson called()");
		List<Person> listOfPersons = new ArrayList();
		listOfPersons.add(new Person(1, "ABC", "DEF"));
		listOfPersons.add(new Person(2, "GHI", "JKL"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Flux listPersons = Flux.just(listOfPersons);
		return listPersons;
	}
	
	@Bean
	public Flux<List<String>> listStrings() {
		System.out.println("listStrings called()");
		List<String> stringList = new ArrayList();
		stringList.add("Luke I'm Your Father");
		stringList.add("I'm Gonna give you an offer you can't refuse");
		stringList.add("Say Hello to my little friend");
		Flux<List<String>> listString = Flux.just(stringList);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listString;
	}
}
