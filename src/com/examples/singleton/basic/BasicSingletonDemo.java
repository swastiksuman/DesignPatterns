package com.examples.singleton.basic;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

/**
 * @author swastik
 *	Problem:
 *	1. Reflection can be used to use constructor
 *	2. Serialization can be used to create multiple copies
 */
public class BasicSingletonDemo {

	public static void main(String[] args) {
		BasicSingleton.getInstance().setI();
		BasicSingleton.getInstance().setI();
		BasicSingleton.getInstance().setI();
		System.out.println(BasicSingleton.getInstance().getI());
		BasicSingleton b = SerializationUtils.roundtrip(BasicSingleton.getInstance());
		BasicSingleton.getInstance().setI();
		BasicSingleton.getInstance().setI();
		System.out.println(b.getI());
		System.out.println(BasicSingleton.getInstance().getI());
	}
}

class BasicSingleton implements Serializable{
	
	private static BasicSingleton INSTANCE = new BasicSingleton();
	
	private BasicSingleton(){
		
	}
	
	public static BasicSingleton getInstance(){
		return INSTANCE;
	}
	
	int i=0;
	
	public void setI(){
		i++;
	}
	
	public int getI(){
		return i;
	}
}
