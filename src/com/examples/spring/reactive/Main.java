package com.examples.spring.reactive;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.examples.spring.beans.Person;
import com.examples.spring.reactive.Config;

import reactor.core.publisher.Flux;

public class Main {

	public static void main(String[] args) {
		System.out.println(new Date());
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
        System.out.println(Thread.currentThread());
        PersonService personService = (PersonService) ctx.getBean("personService");  
        Flux<List<Person>> personList = personService.listPerson();//.subscribe(data -> System.out.println(data.toString()));
        System.out.println(new Date());
        StringService stringService = (StringService) ctx.getBean("stringService");
        Flux<List<String>> stringList = stringService.listStrings();//.subscribe(data -> System.out.println(data.toString()));
        System.out.println(Thread.currentThread());
        System.out.println(new Date());
        System.out.println("END");
	}

}
