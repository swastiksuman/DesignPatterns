package com.examples.interfacesegrationprinciple;

/*The interface-segregation principle (ISP) states that no client should be forced to depend on methods it does not use.*/
public class ISP {
	public static void main(String[] args) {
	}
}

class Document {
}

interface Machine {
	void print(Document d);
	void fax(Document d);
	void scan(Document d);
}

class MultiFunctionPrinter implements Machine {

	@Override
	public void print(Document d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fax(Document d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scan(Document d) {
		// TODO Auto-generated method stub

	}

}

class OldFashionedPrinter implements Machine {

	@Override
	public void print(Document d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fax(Document d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scan(Document d) {
		// TODO Auto-generated method stub

	}

}

interface Printer {
	void print(Document d);
}

interface Scanner {
	void scan(Document d);
}

/*
 * YAGNI = You ain't going to need it
 */
class JustAPrinter implements Printer {

	@Override
	public void print(Document d) {
		// TODO Auto-generated method stub
	}
}

class Photocopier implements Printer, Scanner {

	@Override
	public void scan(Document d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(Document d) {
		// TODO Auto-generated method stub

	}

}

interface MultiFunctionDevice extends Printer, Scanner {

}