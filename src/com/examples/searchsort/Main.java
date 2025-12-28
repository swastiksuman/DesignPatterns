package com.examples.searchsort;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Search and Sort Examples!");

    }
}

/*
Linear Search:
[2,1,3,5,4,7]
search(5)
iteration runs n times
x=n
O(n)
Binary Search:
[1,3,4,7,9,10,15]
search(15)
n/2^x -> x is the number of iterations
x=log2(n)
O(log₂n)

Big O notation -> Time complexity in worst case scenario

O(1) -> Constant Time Complexity
 Always finds array in 1 iteration example: accessing an array element by index

Space Complexity: amount of memory used by an algorithm as the input size grows

Find max element in an array: Space Complexity O(1) Time Complexity O(n)
Time Complexity alway takes the greater value.
 */
