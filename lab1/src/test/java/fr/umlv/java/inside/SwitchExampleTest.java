package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SwitchExampleTest {

	@Test
	@Tag("switchExample")
	public void testSwitchExampleReturn1() {
		assertEquals(1, SwitchExample.switchExample("dog"));
	}

	@Test
	@Tag("switchExample")
	public void testSwitchExampleReturn2() {
		assertEquals(2, SwitchExample.switchExample("cat"));
	}

	@Test
	@Tag("switchExample")
	public void testSwitchExampleReturnDefault4() {
		assertEquals(4, SwitchExample.switchExample("shark"));
	}

	@Test
	@Tag("switchExample")
	public void testSwitchExampleThrowsNPE() {
		assertThrows(NullPointerException.class, () -> SwitchExample.switchExample(null));
	}

}
