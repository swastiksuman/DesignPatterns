package com.examples.singleton.lazysingleton;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

public class LazySingletonDemo {
	public static void main(String[] args) {
		LazySingleton.getInstance();
		System.out.println(LazySingleton.getInstance().hashCode());
		LazySingleton l = LazySingleton.getInstance();
		l = SerializationUtils.roundtrip(l);
		System.out.println(l.hashCode());
		System.out.println(LazySingleton.getInstance().hashCode());
	}
}

class LazySingleton implements Serializable {
	private static LazySingleton instance;

	private LazySingleton() {
		System.out.println("Initializing Singleton");
	}

	// double check locking
	public static /* synchronized */ LazySingleton getInstance() {
		if (instance == null) {
			synchronized (LazySingleton.class) {
				if (instance == null)
					instance = new LazySingleton();
			}
		}
		return instance;
	}

	// Added read resolve to avoid breaking of singleton on serialization
	protected Object readResolve() {
		System.out.println("Read Resolve");
		return instance;
	}
}
