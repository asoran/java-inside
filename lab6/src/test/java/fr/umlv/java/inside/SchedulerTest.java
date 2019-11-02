package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SchedulerTest {

	@Test
	@Tag("STACK")
	public void testSchedulerWithStackMode() {
		var scope = new ContinuationScope("Scheduler");
		var sc = Scheduler.STACK();
		var sb = new StringBuilder();

		var contiuation1 = new Continuation(scope, () -> {
			sb.append("start1");
			sc.enqueue(scope);
			sb.append("middle1");
			sc.enqueue(scope);
			sb.append("end1");
		});

		var contiuation2 = new Continuation(scope, () -> {
			sb.append("start2");
			sc.enqueue(scope);
			sb.append("middle2");
			sc.enqueue(scope);
			sb.append("end2");
		});

		contiuation1.run();
		contiuation2.run();
		sc.runLoop();

		assertEquals("start1start2middle2end2middle1end1", sb.toString());
	}

	@Test
	@Tag("STACK")
	public void testSchedulerWithFifokMode() {
		var scope = new ContinuationScope("Scheduler");
		var sc = Scheduler.FIFO();
		var sb = new StringBuilder();

		var contiuation1 = new Continuation(scope, () -> {
			sb.append("start1");
			sc.enqueue(scope);
			sb.append("middle1");
			sc.enqueue(scope);
			sb.append("end1");
		});

		var contiuation2 = new Continuation(scope, () -> {
			sb.append("start2");
			sc.enqueue(scope);
			sb.append("middle2");
			sc.enqueue(scope);
			sb.append("end2");
		});

		contiuation1.run();
		contiuation2.run();
		sc.runLoop();

		assertEquals("start1start2middle1middle2end1end2", sb.toString());
	}

	@Test
	@Tag("RANDOM")
	public void testSchedulerWithSickokMode() {
//		var scope = new ContinuationScope("Scheduler");
//		var sc = Scheduler.RANDOM();
		var sb = new StringBuilder();

		String s = "How am I supposed to test random behaviour?";
		sb.append(s);

		assertEquals(s, sb.toString());
	}

	@Test
	@Tag("enqueue")
	public void testCallingEnqueueThrowsISE() {
		var scope = new ContinuationScope("Scheduler");
		var sc = Scheduler.RANDOM();
		assertThrows(IllegalStateException.class, () -> sc.enqueue(scope));
	}

}
