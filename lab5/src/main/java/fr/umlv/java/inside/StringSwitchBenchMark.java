package fr.umlv.java.inside;

import java.util.concurrent.TimeUnit;
import java.util.function.ToIntFunction;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 0, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class StringSwitchBenchMark {
//	@Param({"foo", "bar", "baz", "String1", "yikes", "This is definitly not a"
//			+ " very long string to test our methods"})
//	private String strings;

	private void do_tests(ToIntFunction<String> func) {
		for(int i = 0; i < 1_000_000; ++i) {
			func.applyAsInt("foo");
			func.applyAsInt("bar");
			func.applyAsInt("baz");
			func.applyAsInt("String1");
			func.applyAsInt("This is definitly not a very long string to test our methods");
			func.applyAsInt("yikes");
		}
	}

	@Benchmark
	public void stringSwitch_1() {
		do_tests(StringSwitchExample::stringSwitch);
	}

	@Benchmark
	public void stringSwitch_2() {
		do_tests(StringSwitchExample::stringSwitch2);
	}

	@Benchmark
	public void stringSwitch_3() {
		do_tests(StringSwitchExample::stringSwitch3);
	}

}
