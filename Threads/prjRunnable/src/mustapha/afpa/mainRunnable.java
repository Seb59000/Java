package mustapha.afpa;

public class mainRunnable {

	public static void main(String[] args) {
		
		myRunnable myR1 = new myRunnable("test A");
		myR1.start();
		
		myRunnable myR2 = new myRunnable("         test B");
		myR2.start();
		
	}

}
