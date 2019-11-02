package fr.umlv.java.inside;

public class Example1 {

	public static void main(String[] args) {
		var scope = new ContinuationScope("Scheduler");
		var sc = Scheduler.STACK();

		var contiuation1 = new Continuation(scope, () -> {
			System.out.println("start 1");
			sc.enqueue(scope);
			System.out.println("middle 1");
			sc.enqueue(scope);
			System.out.println("end 1");
		});

		var contiuation2 = new Continuation(scope, () -> {
			System.out.println("start 2");
			sc.enqueue(scope);
			System.out.println("middle 2");
			sc.enqueue(scope);
			System.out.println("end 2");
		});

		var contiuation3 = new Continuation(scope, () -> {
			System.out.println("start 3");
			sc.enqueue(scope);
			System.out.println("middle 3");
			sc.enqueue(scope);
			System.out.println("end 3");
		});

		contiuation1.run();
		contiuation2.run();
		contiuation3.run();
		sc.runLoop();

	}

}
