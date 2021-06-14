package com.examples.spring.beans;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
        
        Person a = (Person) ctx.getBean(Person.class);
        Person b = (Person) ctx.getBean("personwa");
        System.out.println(a.toString());
        System.out.println(b.toString());
	}

}
