package com.examples.singleton.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
        
        AnimalFactory a = ctx.getBean(AnimalFactory.class);
        a.getAnimal("Amphibian").sleep();
	}

}
