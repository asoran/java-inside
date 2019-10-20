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
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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

	private static class DisabledLogger {
		static {
			Logger.enable(DisabledLogger.class, false);
		}

		private final static Logger LOGGER = Logger.fastOf(DisabledLogger.class, System.out::println);
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
	public void disabled_logger() {
		System.out.println("AVANT");
		DisabledLogger.LOGGER.log("");
		System.out.println("APRES");
	}
}
