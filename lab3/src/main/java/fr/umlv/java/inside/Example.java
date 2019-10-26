package fr.umlv.java.inside;

public class Example {

	@SuppressWarnings("unused")
	private static String aStaticHello(int value) {
		return "question " + value;
	}

	@SuppressWarnings("unused")
	private String anInstanceHello(int value) {
		return "question " + value;
	}

}
