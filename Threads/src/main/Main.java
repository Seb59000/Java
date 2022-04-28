package main;

import model.*;


public class Main {

	public static void main(String[] args) {
		exo1();
//		exo2();
	}

	private static void exo2() {
		ThreadExo2 t = new ThreadExo2();
		t.start();
	}

	private static void exo1() {
//		affichageLettreAlphabet();
//		affichageSuiteNombre();
		myRunnable myR1 = new myRunnable();
		myR1.start();
		
		myRunnable2 myR2 = new myRunnable2();
		myR2.start();
	}

	private static void affichageLettreAlphabet() {
        char c;

        for(c = 'a'; c <= 'z'; ++c)
            System.out.println(c + " ");
	}

	private static void affichageSuiteNombre() {
		for (int i = 1; i < 27; i++) {
			System.out.println(i);
		}
	}

}
