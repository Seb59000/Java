package model;

public class ThreadExo2 extends Thread {
	Thread2Exo2 t1;
	Thread2Exo2 t2;
	Thread2Exo2 t3;

	public void run() {

		t1 = new Thread2Exo2("Thread 1 ");
		t2 = new Thread2Exo2("Thread 2 ");
		t3 = new Thread2Exo2("Thread 3 ");

		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Programme principal terminé.");

	}
}
