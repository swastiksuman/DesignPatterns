package com.examples.singleton.spring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "singleton")
public class Employee /* implements InitializingBean, DisposableBean */ {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {

		System.out.println("Bean Employee has been " + "instantiated and I'm the " + "init() method");

	}

	@PreDestroy
	public void destroy() throws Exception {
		System.out.println("Bean Employee has been " + "destroyed and I'm the " + "destroy() method");
	}
}