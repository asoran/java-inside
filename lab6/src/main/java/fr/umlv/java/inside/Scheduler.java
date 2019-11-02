package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.concurrent.ThreadLocalRandom;

public class Scheduler {
	private final ArrayDeque<Continuation> deque;
	private final POLITIC pol;

	public enum POLITIC {
		STACK, FIFO, RANDOM
	}

	public Scheduler(Scheduler.POLITIC pol) {
		this.deque = new ArrayDeque<>();
		this.pol = pol;
	}

	public void enqueue(ContinuationScope scope) {
		var cont = Continuation.getCurrentContinuation(scope);
		if(cont == null)		
			throw new IllegalStateException();

		deque.offer(cont);
		Continuation.yield(scope);
	}

	public void runLoop() {
		while(!deque.isEmpty()) {
			Continuation cont;

			switch(pol) {
				case STACK:
					cont = deque.poll();
					break;
				case FIFO:
					cont = deque.poll();
					break;
				case RANDOM:

					var contArray = deque.toArray(new Continuation[]{});
					var index = ThreadLocalRandom.current()
							.nextInt(deque.size());
					cont = contArray[index];
					deque.remove(cont);

					break;
				default:
					throw new IllegalStateException();
			}

			cont.run();
		}
	}

}
