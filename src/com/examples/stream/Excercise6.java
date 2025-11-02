package com.examples.stream;

import java.io.IOException;
import java.time.Duration;

public class Excercise6 {

	public static void main(String[] args) throws IOException {

		// Use ReactiveSources.unresponsiveFlux() and ReactiveSources.unresponsiveMono()

		// Get the value from the Mono into a String variable but give up after 5
		// seconds
		String foo = ReactiveSources.unresponsiveMono().block(Duration.ofSeconds(15));
		System.out.println(foo);
		// Get the value from unresponsiveFlux into a String list but give up after 5
		// seconds
		java.util.List<String> list = ReactiveSources.unresponsiveFlux()
				.collectList()
				.block(Duration.ofSeconds(15));
		System.out.println(list);

		System.out.println("Press a key to end");
		System.in.read();
	}

}