package mustapha.afpa;

public class myRunnable implements Runnable {

	private String msg = "hello";
	private Thread th;
	
	
	public myRunnable(String msg) {
		this.msg = msg;		
	}

	@Override
	public void run() {
		for(int i = 1 ; i<=10 ; i++) {
			System.out.println("je suis dans la fonction run, je  dis ("+i+") : "+this.msg);
		}		
	}
	
	public void start() {
		System.out.println("le thread commence ici...");
		if(th == null) {
			th = new Thread(this);
			th.start();
		}		
	}	
}
