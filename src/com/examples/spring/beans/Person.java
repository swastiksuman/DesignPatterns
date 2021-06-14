package com.examples.spring.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Person implements InitializingBean, DisposableBean{
	
	private int id;
	
	private String firstName;
	
	private String lastName;

	public Person() {
		
	}
	public Person(int id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	@Override
	public void destroy() throws Exception {
		System.out.println("destroy person");
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("init person");
		
	}
	
	
	
}
