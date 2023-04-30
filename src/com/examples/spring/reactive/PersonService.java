package com.examples.spring.reactive;

import java.util.ArrayList;
import java.util.List;

import com.examples.spring.beans.Person;

import reactor.core.publisher.Flux;

public class PersonService {

	public Flux<List<Person>> listPerson() {
		System.out.println("listPerson called()");
		List<Person> listOfPersons = new ArrayList();
		listOfPersons.add(new Person(1, "ABC", "DEF"));
		listOfPersons.add(new Person(2, "GHI", "JKL"));
		System.out.println(Thread.currentThread());
		try {
			System.out.println("PersonService::listPerson "+Thread.currentThread());
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Flux listPersons = Flux.just(listOfPersons);
		return listPersons;
	}
}
