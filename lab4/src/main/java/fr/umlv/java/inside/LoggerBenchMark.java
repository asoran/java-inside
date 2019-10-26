package fr.umlv.java.inside;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class LoggerBenchMark {
	private static class LoggerClass {
		private final static Logger LOGGER = Logger.fastOf(LoggerClass.class, __ -> {});
	}

	private static class DisabledPrintLogger {
		static {
			Logger.enable(DisabledPrintLogger.class, false);
		}

		private final static Logger LOGGER = Logger.fastOf(DisabledPrintLogger.class, System.out::println);
	}
	
	private static class DisabledEmptyLogger {
		static {
			Logger.enable(DisabledEmptyLogger.class, false);
		}

		private final static Logger LOGGER = Logger.fastOf(DisabledEmptyLogger.class, __ -> {});
	}

	@Benchmark
	public void no_op() {
		// empty
	}

	@Benchmark
	public void simple_logger() {
		LoggerClass.LOGGER.log("");
	}

	@Benchmark
	public void disabled_print_logger() {
		DisabledPrintLogger.LOGGER.log("");
	}

	@Benchmark
	public void disabled_empty_logger() {
		DisabledEmptyLogger.LOGGER.log("");
	}
}
