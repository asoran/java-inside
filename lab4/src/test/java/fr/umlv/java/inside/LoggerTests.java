package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		private final static StringBuilder SB = new StringBuilder();
		private final static Logger LOGGER = Logger.of(LoggerClass.class, SB::append);
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

	private static class DisabledLogger {
		static {
			Logger.enable(DisabledLogger.class, false);
		}

		private final static StringBuilder SB = new StringBuilder();
		private final static Logger LOGGER = Logger.fastOf(LoggerClass.class, SB::append);
	}

	@Test
	public void disabled_logger() {
		DisabledLogger.LOGGER.log("");
		assertTrue(DisabledLogger.SB.toString().isEmpty());
	}
	
}
