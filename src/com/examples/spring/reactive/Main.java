package com.examples.spring.reactive;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.examples.spring.beans.Person;
import com.examples.spring.reactive.Config;

import reactor.core.publisher.Flux;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
        
        Flux<List<Person>> fluxPerson = (Flux<List<Person>>) ctx.getBean("listPerson");
        fluxPerson.subscribe(data -> System.out.println(data.toString()));
        
	}

}
