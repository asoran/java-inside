package fr.umlv.java.inside;

import static java.lang.invoke.MethodHandles.constant;
import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Objects;

public class StringSwitchExample {
	private static final MethodHandle MOINS_ICHI = MethodHandles.dropArguments(MethodHandles.constant(int.class, -1), 0,
			String.class);

	private static MethodHandle STRING_EQUALS;

	static {
		try {
			STRING_EQUALS = MethodHandles.lookup().findVirtual(String.class, "equals",
					methodType(boolean.class, Object.class));

		} catch (NoSuchMethodException | IllegalAccessException e) {
			throw new AssertionError();
		}
	}

	/* **************************************************** */

	public static int stringSwitch(String string) {
		Objects.requireNonNull(string);

		switch (string) {
		case "foo":
			return 0;
		case "bar":
			return 1;
		case "bazz":
			return 2;
		default:
			return -1;
		}
	}

	private static MethodHandle createMHFromStrings(String... string) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/* **************************************************** */

	public static int stringSwitch2(String string) {
		try {
			var mh = createMHFromStrings2("foo", "bar", "bazz");
			return (int) mh.invokeExact(string);

		} catch (RuntimeException | Error e) {
			throw e;
		} catch (Throwable t) {
			throw new UndeclaredThrowableException(t);
		}
	}

	private static MethodHandle createMHFromStrings2(String... string) throws Throwable {
		var mh = MOINS_ICHI;

		for (int i = 0; i < string.length; ++i) {
			mh = MethodHandles.guardWithTest(insertArguments(STRING_EQUALS, 1, string[i]),
					dropArguments(constant(int.class, i), 0, String.class), mh);
		}

		return mh;
	}

	/* **************************************************** */
	


}
