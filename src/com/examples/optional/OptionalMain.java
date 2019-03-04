package com.examples.optional;

import java.util.Optional;
import java.util.function.Function;

public class OptionalMain {
    public static void main(String[] args){
        Function<String, String> getSecondWord = (String s) -> {
            return s.split(" ").length > 1 ? s.split(" ")[1] : null;
        };

        Function<String, Integer> getLetterCount = t -> t.length();
        Integer count = getSecondWord.andThen(getLetterCount).apply("Swastik Sum");
        System.out.println(count);

        Optional.ofNullable(getSecondWord.apply("Swastik Suman")).map(getLetterCount)
                .ifPresent(System.out::println);

    }
}
