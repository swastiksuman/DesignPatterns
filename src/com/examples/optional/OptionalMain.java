package com.examples.optional;

import java.util.Optional;
import java.util.function.Function;

public class OptionalMain {
    public static void main(String[] args){
        // Legacy nullable-returning function (not recommended)
        Function<String, String> getSecondWord = s -> {
            String[] parts = s.split(" ");
            return parts.length > 1 ? parts[1] : null;
        };

        Function<String, Integer> getLetterCount = t -> t.length();
        Integer count = getSecondWord.andThen(getLetterCount).apply("Swastik Sum");
        System.out.println(count);

        // Recommended: use a helper that returns Optional to avoid nullable intermediates
        getSecondWordOptional("Swastik Suman")
                .map(getLetterCount)
                .ifPresent(System.out::println);

    }

    static Optional<String> getSecondWordOptional(String s) {
        String[] parts = s.split(" ");
        return parts.length > 1 ? Optional.of(parts[1]) : Optional.empty();
    }
}
