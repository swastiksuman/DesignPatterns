package com.examples.stream;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Excercise21 {
	public static void main(String[] args) {
		fetchAllUser()
			.toStream()
			.forEach(System.out::println);
	}
	
	public static Flux<User> fetchAllUser() {
		return Flux.mergeSequential(
			ReactiveSources.userFlux(),
			Flux.defer(() -> ReactiveSources.newUserfFlux() != null ? 
				Flux.from(ReactiveSources.newUserfFlux()) : 
				Flux.empty())
		)
		.onErrorResume(e -> {
			System.err.println("Error occurred: " + e.getMessage());
			return Flux.empty();
		});
	}
}
