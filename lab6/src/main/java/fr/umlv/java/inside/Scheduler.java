package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public interface Scheduler {

	public static Scheduler STACK() {
		return new Scheduler() {
			private final ArrayDeque<Continuation> list
				= new ArrayDeque<>();

			@Override
			public boolean isEmpty() {
				return list.isEmpty();
			}

			@Override
			public Continuation getNext() {
				return list.pollLast();
			}

			@Override
			public void addElement(Continuation cont) {
				list.offer(cont);
			}
		};
	}

	public static Scheduler FIFO() {
		return new Scheduler() {
			private final ArrayDeque<Continuation> list
				= new ArrayDeque<>();

			@Override
			public boolean isEmpty() {
				return list.isEmpty();
			}

			@Override
			public Continuation getNext() {
				return list.poll();
			}

			@Override
			public void addElement(Continuation cont) {
				list.offer(cont);
			}
		};
	}

	public static Scheduler RANDOM() {
		return new Scheduler() {
			private final ArrayList<Continuation> list
				= new ArrayList<>();

			@Override
			public boolean isEmpty() {
				return list.isEmpty();
			}

			@Override
			public Continuation getNext() {
				int rand = ThreadLocalRandom.current().nextInt(list.size());
				return list.remove(rand);
			}

			@Override
			public void addElement(Continuation cont) {
				list.add(cont);
			}
		};
	}

	void addElement(Continuation cont);
	Continuation getNext();
	boolean isEmpty();

	default public void enqueue(ContinuationScope scope) {
		var cont = Continuation.getCurrentContinuation(scope);
		if(cont == null)		
			throw new IllegalStateException();

		addElement(cont);
		Continuation.yield(scope);
	}

	default public void runLoop() {
		while(!isEmpty()) {
			Continuation cont = getNext();
			cont.run();
		}
	}

}
