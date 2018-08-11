package com.examples.singleton.monostate;

public class MonostateDemo {
	public static void main(String[] args){
		ChiefExecutiveOfficer swastik = new ChiefExecutiveOfficer("Swastik", 29);
		ChiefExecutiveOfficer shankar = new ChiefExecutiveOfficer("Shankar", 58);
		System.out.println(swastik);
		System.out.println(shankar);
	}
}

class ChiefExecutiveOfficer{
	public static String name;
	public static int age;
	
	public ChiefExecutiveOfficer(String name, int age){
		ChiefExecutiveOfficer.name = name;
		ChiefExecutiveOfficer.age = age;
	}
	
	public void setName(String name){
		ChiefExecutiveOfficer.name = name;
	}
	
	public String getName(){
		return ChiefExecutiveOfficer.name;
	}
	
	public void setAge(int age){
		ChiefExecutiveOfficer.age = age;
	}
	
	public int getAge(){
		return ChiefExecutiveOfficer.age;
	}

	@Override
	public String toString() {
		return "ChiefExecutiveOfficer " + name + " Age: " + age;
	}
	
	
}
