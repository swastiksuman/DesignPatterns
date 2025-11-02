package com.examples.stream;

import reactor.core.publisher.Mono;

public class Excercise22 {
    public static void main(String[] args) {
        FetchMdnService fetchMdnService = new FetchMdnService();
        
        // Get the MDN and update the cart using reactive streams
        Mono.just(fetchMdnService.getMdn())
            .flatMap(mdn -> 
                fetchMdnService.getCart()
                    .map(cart -> {
                        cart.setMdn(mdn);
                        return cart;
                    })
            )
            .subscribe(
                cart -> System.out.println("Cart updated with MDN: " + cart.getMdn()),
                error -> System.err.println("Error occurred: " + error),
                () -> System.out.println("Operation completed")
            );

        // Keep main thread alive to see results
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
