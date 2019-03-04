package com.examples.functional;

public class CustomLambdaExpression {
    public static void main(String[] args){
        CustomStringInterface concatStrings = (arg1, arg2) -> arg1+" "+arg2;
        System.out.println(concatStrings.doSomethingWith2Strings("Swastik", "Soujanya"));
        CustomStringInterface getMatchingCharsInString = (arg1, arg2) -> {
            String matchingCharacters = "";
          for(char c: arg1.toCharArray()){
            if(arg2.indexOf(c)>-1){
                matchingCharacters = matchingCharacters+c;
            }
          }
          return matchingCharacters;
        };
        System.out.println(getMatchingCharsInString.doSomethingWith2Strings("Swastik", "Soujanya"));
    }
}

@FunctionalInterface
interface CustomStringInterface{
    String doSomethingWith2Strings(String arg1, String arg2);
}
