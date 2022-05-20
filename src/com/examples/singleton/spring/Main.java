package com.examples.singleton.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
        
        Employee a = (Employee)ctx.getBean("employee");
        
        a.setName("Swastik");
        Employee b = ctx.getBean(Employee.class);
        System.out.println(b.getName());
        System.out.println(a == b);
        ctx.close();
	}

}
