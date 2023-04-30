package com.examples.spring.reactive;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Flux;

public class StringService {
	public Flux<List<String>> listStrings() {
		System.out.println("listStrings called()");
		List<String> stringList = new ArrayList();
		stringList.add("Luke I'm Your Father");
		stringList.add("I'm Gonna give you an offer you can't refuse");
		stringList.add("Say Hello to my little friend");
		Flux<List<String>> listString = Flux.just(stringList);
		System.out.println(Thread.currentThread());
		try {
			System.out.println("StringService::listStrings "+Thread.currentThread());
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listString;
	}
}
