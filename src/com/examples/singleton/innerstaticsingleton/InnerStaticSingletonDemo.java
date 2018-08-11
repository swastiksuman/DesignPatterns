package com.examples.singleton.innerstaticsingleton;

public class InnerStaticSingletonDemo {
	public static void main(String[] args) {
		InnerStaticSingleton.getInstance();
	}
}

class InnerStaticSingleton {
	private InnerStaticSingleton() {
	}

	private static class Impl {
		private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
	}

	public static InnerStaticSingleton getInstance() {
		return Impl.INSTANCE;
	}
}
