package com.examples.spring.interceptor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
	}
}
