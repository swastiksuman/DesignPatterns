package com.examples.callablefuture;

import java.util.Random;
import java.util.concurrent.Callable;

class CallableExample implements Callable 
{ 
  
  public Object call() throws Exception 
  { 
    Random generator = new Random(); 
    Integer randomNumber = generator.nextInt(5); 
  
    Thread.sleep(randomNumber * 1000); 
  
    return randomNumber; 
  } 
  
} 