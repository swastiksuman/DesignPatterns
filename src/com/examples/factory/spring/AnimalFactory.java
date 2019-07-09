package com.examples.factory.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalFactory {
	 @Autowired
	    private List<Animal> animals;

	    private static final Map<String, Animal> myServiceCache = new HashMap<>();

	    @PostConstruct
	    public void initMyServiceCache() {
	        for(Animal animal : animals) {
	            myServiceCache.put(animal.getType(), animal);
	        }
	    }

	    public static Animal getAnimal(String type) {
	        Animal animal = myServiceCache.get(type);
	        if(animal == null) throw new RuntimeException("Unknown service type: " + type);
	        return animal;
	    }
	public void print() {
		System.out.println("+++HELLO+++");
	}
}
