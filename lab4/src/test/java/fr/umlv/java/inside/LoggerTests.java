package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class LoggerTests {
	
	@Test
	@Tag("Question2")
	public void LoggerRequireNotNullClass() {
		assertThrows(NullPointerException.class, () -> Logger.of(null, System.err::println));
	}

	@Test
	@Tag("Question2")
	public void LoggerRequireNotNullConsumer() {
		assertThrows(NullPointerException.class, () -> Logger.of(LoggerTests.class, null));
	}

	private static class LoggerClass {
		private final static StringBuilder sb = new StringBuilder();
		private final static Logger logger = Logger.of(LoggerTests.class, sb::append);
	}

	@Test
	@Tag("Question2")
	public void LoggerLogsGoodLol() {
		LoggerClass.logger.log("Salut");
		assertEquals("Salut", LoggerClass.sb.toString());
	}


}
