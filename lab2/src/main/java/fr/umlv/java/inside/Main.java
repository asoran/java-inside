package fr.umlv.java.inside;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

public class Main {

	private final static ClassValue<Method[]> cachedMethods = new ClassValue<Method[]>() {
		@Override
		protected Method[] computeValue(Class<?> type) {
			// System.out.println("Test :)");
			return type.getMethods();
		}
	};

	private final static ClassValue<Function<Method, String>> cachedMethodsName = new ClassValue<Function<Method, String>>() {
		@Override
		protected Function<Method, String> computeValue(Class<?> type) {
			// System.out.println("Test :)");
			var methodNames = Stream.of(type.getMethods())
				.filter(m -> isGeter(m.getName()))
				.filter(m -> m.isAnnotationPresent(JSONProperty.class))
				.collect(toMap(
					m -> m,
					m -> getMethodeJsonName(m))
				);

			return method -> methodNames.get(method);
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

	private static String getMethodeJsonName(Method m) {
		var anno = m.getAnnotation(JSONProperty.class);
		return anno.name().isEmpty() ?
				propertyName(m.getName()) : anno.name(); 
	}

	private static String methodToFieldDeuxPointsValue(Class clazz, Method m,
		Object that) {

		var propName = cachedMethodsName.get(clazz).apply(m);

		var propValue = callGetter(m, that);

		return propName + " : " + propValue.toString();
	}

	public static String toJSON(Object o) {
		var clazz = o.getClass();
		var methods = Stream.of(cachedMethods.get(clazz));

		var nameValueMap = methods
			.filter(m -> isGeter(m.getName()))
			.filter(m -> m.isAnnotationPresent(JSONProperty.class))
			.sorted(Comparator.comparing(Method::getName))
			.map(m -> methodToFieldDeuxPointsValue(clazz, m, o))
			.collect(joining(",\n\t", "{\n\t", "\n}"))
			;

		return nameValueMap;
	}

}
