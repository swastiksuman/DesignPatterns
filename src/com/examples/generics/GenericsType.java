package com.examples.generics;

/*Java Generic Type Naming convention helps us understanding code easily and having a naming convention is one of the best practices of java programming language. So generics also comes with it’s own naming conventions. Usually type parameter names are single, uppercase letters to make it easily distinguishable from java variables. The most commonly used type parameter names are:

E – Element (used extensively by the Java Collections Framework, for example ArrayList, Set etc.)
K – Key (Used in Map)
N – Number
T – Type
V – Value (Used in Map)
S,U,V etc. – 2nd, 3rd, 4th types*/

public class GenericsType<T> {
	private T t;

	public T get() {
		return this.t;
	}

	public void set(T t1) {
		this.t = t1;
	}
	
	
	public static void main(String args[]) {
		GenericsType<String> type = new GenericsType<>();
		type.set("Swastik"); // valid
		System.out.println(type);
		
		//Should always try avoiding this though valid
		GenericsType type1 = new GenericsType(); // raw type
		type1.set("Swastik"); // valid
		type1.set(10); // valid and autoboxing support
		System.out.println(type1);
	}

	@Override
	public String toString() {
		return "GenericsType [t=" + t + "]";
	}
}
