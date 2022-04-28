package model;

public class myRunnable2 extends Thread {

	public myRunnable2() {
	}

	@Override
	public void run() {
		affichageLettreAlphabet();
	}


	private static void affichageLettreAlphabet() {
		char c;

		for (c = 'a'; c <= 'z'; ++c)
			System.out.println(c + " ");
	}
}
