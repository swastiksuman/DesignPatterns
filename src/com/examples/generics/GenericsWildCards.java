package com.examples.generics;

import java.util.ArrayList;
import java.util.List;

/*Generics UnBounded
 * public static void printData(List<?> list){
	for(Object obj : list){
		System.out.print(obj + "::");
	}
}*/

//Example of UpperBound and lowerBound wildcard
public class GenericsWildCards {

	public static void main(String[] args) {
		List<Integer> ints = new ArrayList<>();
		ints.add(3); ints.add(5); ints.add(10);
		double sum = sum(ints);
		System.out.println("Sum of ints="+sum);
	}

	public static double sum(List<? extends Number> list){
		double sum = 0;
		for(Number n : list){
			sum += n.doubleValue();
		}
		return sum;
	}
}
/* Why Generics doesn't support sub-typing
List<Long> listLong = new ArrayList<Long>();
listLong.add(Long.valueOf(10));
List<Number> listNumbers = listLong; // compiler error
listNumbers.add(Double.valueOf(1.23)); // if no error then here a double would have been added to a Long list
*/
