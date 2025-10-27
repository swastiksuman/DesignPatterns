package com.examples.stream;

import java.io.IOException;
import java.time.Duration;

import reactor.core.publisher.Flux;

public class Excercise3 {

	public static void main(String[] args) throws IOException {
		System.out.println("Starts");

		// flux emits one element per second
		Flux<Character> flux = Flux.just('a', 'b', 'c', 'd').delayElements(Duration.ofSeconds(1));
		// Observer 1 - takes 500ms to process
		flux.map(Character::toUpperCase).subscribe(i -> {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Observer-1 : " + i);
		});
		// Observer 2 - process immediately
		flux.subscribe(i -> System.out.println("Observer-2 : " + i));

		System.out.println("Ends");
		System.in.read();
	}

}
