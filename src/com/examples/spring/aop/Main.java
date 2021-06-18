package com.examples.spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
        
        PersonService personService = (PersonService) ctx.getBean(PersonService.class);
        System.out.println(personService.getPerson());
	}

}
