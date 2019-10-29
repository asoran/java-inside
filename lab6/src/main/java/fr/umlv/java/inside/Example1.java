package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.List;

public class Example1 {

	public static void main(String[] args) {
		var scope = new ContinuationScope("scope");

		var contiuation1 = new Continuation(scope, () -> {
			System.out.println("start 1");
			Continuation.yield(scope);
			System.out.println("middle 1");
			Continuation.yield(scope);
			System.out.println("end 1");
		});

		var contiuation2 = new Continuation(scope, () -> {
			System.out.println("start 2");
			Continuation.yield(scope);
			System.out.println("middle 2");
			Continuation.yield(scope);
			System.out.println("end 2");
		});

		var contiuation3 = new Continuation(scope, () -> {
			System.out.println("start 3");
			Continuation.yield(scope);
			System.out.println("middle 3");
			Continuation.yield(scope);
			System.out.println("end 3");
		});

		var list = List.of(contiuation1, contiuation2, contiuation3);

		var deck = new ArrayDeque<>(list);
		while(!deck.isEmpty()) {
			var e = deck.poll();
			e.run();
			if(!e.isDone())
				deck.offer(e);
		}

	}

}
