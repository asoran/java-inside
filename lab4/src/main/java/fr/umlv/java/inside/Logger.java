package fr.umlv.java.inside;

import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodType.methodType;
import static java.util.Objects.requireNonNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.function.Consumer;

/**

Doc de 'getTarget':
In particular, the current thread may choose to reuse the result of a previous read of
the target from memory, and may fail to see a recent update to the target by another thread.

syncAll

**/


public interface Logger {
	public void log(String message);

	static final ClassValue<MutableCallSite> ENABLE_CALLSITES = new ClassValue<>() {
		protected MutableCallSite computeValue(Class<?> type) {
			return new MutableCallSite(MethodHandles.constant(boolean.class, true));
		}
	};

	public static void enable(Class<?> declaringClass, boolean enable) {
		var site = ENABLE_CALLSITES.get(declaringClass);
		site.setTarget(MethodHandles.constant(boolean.class, enable));

		MutableCallSite.syncAll(new MutableCallSite[] {site});
	}

	public static Logger fastOf(Class<?> declaringClass, Consumer<? super String> consumer) {
		requireNonNull(declaringClass);
		requireNonNull(consumer);

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
		requireNonNull(declaringClass);
		requireNonNull(consumer);

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
		var l = MethodHandles.lookup();
		MethodHandle mh = null;
		
		try {
			mh = l.findVirtual(Consumer.class, "accept", methodType(void.class, Object.class));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			throw new AssertionError(e);
		}

		mh = insertArguments(mh, 0, consumer).asType(methodType(void.class, String.class));

		return MethodHandles.guardWithTest(
			ENABLE_CALLSITES.get(declaringClass).getTarget(),
			mh,
			MethodHandles.empty(methodType(void.class, String.class))
		);
	}
}
