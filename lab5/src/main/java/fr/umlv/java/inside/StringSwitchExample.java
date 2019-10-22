package fr.umlv.java.inside;

import java.lang.invoke.MethodHandle;
import java.util.Objects;

public class StringSwitchExample {

	public static int stringSwitch(String string) {
		Objects.requireNonNull(string);

		switch(string) {
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

	
}
