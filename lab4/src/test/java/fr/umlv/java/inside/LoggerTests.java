package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class LoggerTests {
	
	@Test
	@Tag("Question2")
	public void LoggerRequireNotNullClass() {
		assertThrows(NullPointerException.class, () -> Logger.of(null, __ -> {}));
	}

	@Test
	@Tag("Question2")
	public void LoggerRequireNotNullConsumer() {
		assertThrows(NullPointerException.class, () -> Logger.of(LoggerTests.class, null));
	}

	private static class LoggerClass {
		private final static StringBuilder SB;
		private final static Logger LOGGER;

		static {
			try {
				SB = new StringBuilder();
				LOGGER = Logger.of(LoggerClass.class, SB::append);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	@Test
	@Tag("Question2")
	public void LoggerLogsGoodLol() {
		LoggerClass.LOGGER.log("Salut");
		assertEquals("Salut", LoggerClass.SB.toString());
	}

	@Test
	@Tag("Question2")
	public void LoggerDontLogNull() {
		assertThrows(NullPointerException.class, () -> LoggerClass.LOGGER.log(null));
	}
}
