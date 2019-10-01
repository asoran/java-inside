package fr.umlv.java.inside;

import static java.util.stream.Collectors.joining;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	private static String propertyName(String name) {
		return Character.toLowerCase(name.charAt(3)) + name.substring(4);
	}

	private static Boolean isGeter(String name) {
		return name.startsWith("get");
	}

	public static String toJSON(Object o) {

		var clazz = o.getClass();
		var methods = Stream.of(clazz.getMethods());

		var nameValueMap = methods
			.map(m -> m.getName())
			.filter(Main::isGeter)
			.map(Main::propertyName)
			.collect(joining(", ", "{", "}"));


		return nameValueMap;
	}
	
	public static void main(String[] args) {
		var p = new Person("Altan", "Le bg");
		System.out.println(toJSON(p));
	}

}
