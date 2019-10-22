package fr.umlv.java.inside;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class StringSwitchExampleTests {

	@ParameterizedTest
	@MethodSource("SASAGEYO_SASAGEYO")
	@Tag("Question3")
	public void stringSwitchWorksCorrectly(ToIntFunction<String> m) {
		assertAll(
			() -> assertEquals(0, m.applyAsInt("foo")),
			() -> assertEquals(1, m.applyAsInt("bar")),
			() -> assertEquals(2, m.applyAsInt("bazz")),
			() -> assertEquals(-1, m.applyAsInt("other")),
			() -> assertThrows(NullPointerException.class,
				() -> StringSwitchExample.stringSwitch(null))
		);
	}

	public static Stream<ToIntFunction<String>> SASAGEYO_SASAGEYO() {
		return Stream.of(
			StringSwitchExample::stringSwitch,
			StringSwitchExample::stringSwitch2
		);
	}

}
