package com.examples.stream;

import java.io.IOException;

public class Excercise4 {
	public static void main(String[] args) throws IOException {
		ReactiveSources.intNumberMono().subscribe(number -> System.out.println(number));
		Integer number = ReactiveSources.intNumberMono().block();
		System.out.println("X "+number);
		System.in.read();
	}
}
