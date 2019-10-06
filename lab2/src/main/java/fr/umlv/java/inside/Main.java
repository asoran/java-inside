package fr.umlv.java.inside;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	private final static ClassValue<Method[]> cache = new ClassValue<Method[]>() {
		@Override
		protected Method[] computeValue(Class<?> type) {
			// System.out.println("Test :)");
			return type.getMethods();
		}
	};

	private static String propertyName(String name) {
		return Character.toLowerCase(name.charAt(3)) + name.substring(4);
	}

	private static Boolean isGeter(String name) {
		return name.startsWith("get");
	}

	private static Object callGetter(Method m, Object that) {
		try {
			return m.invoke(that);

		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		} catch (InvocationTargetException e) {
			var cause = e.getCause();
			if(cause instanceof RuntimeException)
				throw (RuntimeException) cause;
			else if(cause instanceof Error)
				throw (Error) cause;
			throw new UndeclaredThrowableException(cause);
		}
	}

	private static String methodToFieldDeuxPointsValue(Method m,
		Object that, Object[] args) {

		var anno = m.getAnnotation(JSONProperty.class);
		var propName = anno.name().isEmpty() ?
				propertyName(m.getName()) : anno.name(); 

		var propValue = callGetter(m, that);

		return propName + " : " + propValue.toString();
	}

	public static String toJSON(Object o) {
		var clazz = o.getClass();
		var methods = Stream.of(cache.get(clazz));

		var nameValueMap = methods
			.filter(m -> isGeter(m.getName()))
			.filter(m -> m.isAnnotationPresent(JSONProperty.class))
			.sorted(Comparator.comparing(Method::getName))
			.map(m -> methodToFieldDeuxPointsValue(m, o, new Object[0]))
			.collect(joining(",\n\t", "{\n\t", "\n}"))
			;

		return nameValueMap;
	}

}
