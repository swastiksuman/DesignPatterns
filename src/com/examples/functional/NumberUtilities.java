package com.examples.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class NumberUtilities {
    public static int findSquareOfMaxOdd(List<Integer> numbers) {
        return numbers.stream()
                .filter(NumberUtilities::isOdd) 		//Predicate is functional interface and
                .filter(NumberUtilities::isGreaterThan3)	// we are using lambdas to initialize it
                .filter(NumberUtilities::isLessThan11)	// rather than anonymous inner classes
                .max(Comparator.naturalOrder())
                .map(i -> i * i)
                .get();
    }

    public static boolean isOdd(int i) {
        return i % 2 != 0;
    }

    public static boolean isGreaterThan3(int i){
        return i > 3;
    }

    public static boolean isLessThan11(int i){
        return i < 11;
    }

    public static void main(String[] args){
        System.out.println(findSquareOfMaxOdd(new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 21, 33, 37))));
    }
}
