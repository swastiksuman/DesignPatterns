package com.examples.stream;

import reactor.core.publisher.Mono;

public class FetchMdnService {
	public String getMdn() {
		return String.format("%010d", (long)(Math.random() * 10000000000L));
	}
	
	public Mono<Cart> getCart(){
		return Mono.just(new Cart("123","8907654321",""));
	}
}
