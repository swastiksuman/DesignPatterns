package com.examples.reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectCreation {
	public static void main(String[] args)
			throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// 1 - By using Class.forname() method
		Class c1 = Class.forName("com.examples.reflections.ObjectCreation");
		for (Method m : c1.getMethods()) {

		}
		// 2- By using getClass() method
		ObjectCreation obj = new ObjectCreation();
		Class c2 = obj.getClass();
		int c = 2;
		for (Method m : c2.getDeclaredMethods()) {
			if (!m.getName().contains("main")) {
				System.out.println(m.invoke(obj, c++));
			}
		}

		// 3- By using .class
		Class c3 = ObjectCreation.class;
		/*
		 * for(Method m: c3.getMethods()){ System.out.println(m.getName()); }
		 */
	}

	public String getData(int i) {
		return "Data: " + i;
	}
}