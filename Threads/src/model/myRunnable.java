package model;

public class myRunnable extends Thread {

	public myRunnable() {
	}

	@Override
	public void run() {
		affichageSuiteNombre();
	}

	private static void affichageSuiteNombre() {
		for (int i = 1; i < 27; i++) {
			System.out.println(i);
		}
	}
}
