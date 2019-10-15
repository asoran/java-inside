package fr.umlv.java.inside;

import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodType.methodType;
import static java.util.Objects.requireNonNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.function.Consumer;

public interface Logger {
	public void log(String message);

	public static Logger fastOf(Class<?> declaringClass, Consumer<? super String> consumer) {
		var mh = createLoggingMethodHandle(declaringClass, consumer);

		return (message) -> {

			requireNonNull(message);
			try {
				mh.invokeExact(message);
			} catch (Throwable t) {
				if (t instanceof RuntimeException) {
					throw (RuntimeException) t;
				}
				if (t instanceof Error) {
					throw (Error) t;
				}
				throw new UndeclaredThrowableException(t);
			}
		};
	}
	
	public static Logger of(Class<?> declaringClass, Consumer<? super String> consumer) {
		var mh = createLoggingMethodHandle(declaringClass, consumer);
		return new Logger() {
			@Override
			public void log(String message) {
				requireNonNull(message);

				try {
					mh.invokeExact(message);
				} catch (Throwable t) {
					if (t instanceof RuntimeException) {
						throw (RuntimeException) t;
					}
					if (t instanceof Error) {
						throw (Error) t;
					}
					throw new UndeclaredThrowableException(t);
				}
			}
		};
	}

	private static MethodHandle createLoggingMethodHandle(Class<?> declaringClass, Consumer<? super String> consumer) {
		requireNonNull(declaringClass);
		requireNonNull(consumer);

		var l = MethodHandles.lookup();
		MethodHandle mh = null;

		try {
			mh = l.findVirtual(Consumer.class, "accept",
				methodType(void.class, Object.class));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			throw new AssertionError(e);
		}

		mh = insertArguments(mh, 0, consumer)
			.asType(methodType(void.class, String.class));

		return mh;
	}
}
