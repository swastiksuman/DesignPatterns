package com.examples.stream;

import java.io.IOException;

public class Excercise2 {

    public static void main(String[] args) throws IOException, InterruptedException {

        // Use ReactiveSources.intNumbersFlux() and ReactiveSources.userFlux()
    	ReactiveSources.intNumbersFlux().subscribe(System.out::println);
        // Print all numbers in the ReactiveSources.intNumbersFlux stream
        ReactiveSources.userFlux().subscribe(System.out::println);
    	//Thread.sleep(5000);
        // Print all users in the ReactiveSources.userFlux stream
        // TODO: Write code here

        System.out.println("Press a key to end");
        System.in.read();
    }

}