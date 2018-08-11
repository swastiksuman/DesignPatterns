package com.examples.prototype.SerializationClone;

import java.io.Serializable;


public class SerializationDemo {
	public static void main(String[] args) {
		Employee john = new Employee("Swastik", new Address("Nalla", "Hyderabad", "India"));
		//Employee chris = john;  //Normal Way
		Employee chris = org.apache.commons.lang3.SerializationUtils.roundtrip(john); //Shallow Copy not a deep copy
		chris.setName("Chris");
		chris.getAdress().setCity("Puri");
		System.out.println(john);
		System.out.println(chris);
	}
}

class Address implements Serializable{
	private String streetAddress;
	private String city;
	private String country;

	public Address(String streetAddress, String city, String country) {
		super();
		this.streetAddress = streetAddress;
		this.city = city;
		this.country = country;
	}

	public Address(Address other) {
		this(other.streetAddress, other.city, other.country);
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address [streetAddress=" + streetAddress + ", city=" + city + ", country=" + country + "]";
	}
	
	
}

class Employee implements Serializable{
	private String name;
	private Address adress;

	public Employee(String name, Address adress) {
		super();
		this.name = name;
		this.adress = adress;
	}

	public Employee(Employee other) {
		this(other.name, other.adress);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAdress() {
		return adress;
	}

	public void setAdress(Address adress) {
		this.adress = adress;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", adress=" + adress + "]";
	}

}