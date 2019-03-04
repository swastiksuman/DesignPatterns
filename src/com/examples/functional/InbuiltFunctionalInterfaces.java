package com.examples.functional;

import java.util.function.Function;

public class InbuiltFunctionalInterfaces {
    public static void main(String[] args){
        Function<String, String> stripEmptyChars = (arg) -> arg.replace(" ", "");
        System.out.println(stripEmptyChars.apply("Swastik Suman Panda"));
    }
}
