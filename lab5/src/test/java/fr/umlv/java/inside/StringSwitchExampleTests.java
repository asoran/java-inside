package fr.umlv.java.inside;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class StringSwitchExampleTests {

	@Test
	@Tag("Question3")
	public void stringSwitchWorksCorrectly() {
		assertAll(
			() -> assertEquals(0, StringSwitchExample.stringSwitch("foo")),
			() -> assertEquals(1, StringSwitchExample.stringSwitch("bar")),
			() -> assertEquals(2, StringSwitchExample.stringSwitch("bazz")),
			() -> assertEquals(-1, StringSwitchExample.stringSwitch("other"))
		);
	}

	@Test
	@Tag("Question3")
	public void stringSwitchThrowsNPEWhenNullGiven() {
		assertThrows(NullPointerException.class,
			() -> StringSwitchExample.stringSwitch(null));
	}

	
}
