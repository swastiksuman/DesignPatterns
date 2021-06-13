package com.examples.spring.beans;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
        
        Person a = (Person) ctx.getBean("personwa");
        System.out.println(a.toString());
	}

}
