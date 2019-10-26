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

	private static class AnotherLogger {

		private final static StringBuilder SB = new StringBuilder();
		private final static Logger LOGGER = Logger.fastOf(AnotherLogger.class, SB::append);
	}
	
	private static class DisabledLogger {
		static {
			Logger.enable(DisabledLogger.class, false);
		}

		private final static StringBuilder SB = new StringBuilder();
		private final static Logger LOGGER = Logger.fastOf(DisabledLogger.class, SB::append);
	}

	@Test
	@Tag("Question10")
	public void disable_logger_then_log() throws InterruptedException {
		// This can fail ...

		Thread t2 = new Thread(() -> {
			AnotherLogger.LOGGER.log("SALUT");
		});

		Thread t1 = new Thread(() -> {
			Logger.enable(AnotherLogger.class, false);
		});

		t1.start();
		Thread.sleep(1_000);
		t2.start();

		t1.join();
		t2.join();

		assertTrue(AnotherLogger.SB.toString().isEmpty());
	}

	@Test
	@Tag("Question10")
	public void disabled_logger_should_not_log() {
		DisabledLogger.LOGGER.log("C'est la mer noir");
		assertTrue(DisabledLogger.SB.toString().isEmpty());
	}

}
