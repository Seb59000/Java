package mustapha.afpa;

public class myRunnable0 implements Runnable {

	private String msg = "hello";	
	
	public myRunnable0(String msg) {
		this.msg = msg;
		
	}

	@Override
	public void run() {
		for(int i = 1 ; i<=10 ; i++) {
			System.out.println("je suis dans la fonction run, je  dis ("+i+") : "+this.msg);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
