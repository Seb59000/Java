package model;

public class Thread2Exo2 extends Thread {
	Boolean end = false;

	@Override
	public void run() {
		myThreadAction();
	}

	public Thread2Exo2(String nom) {
		this.setName(nom);
	}

	private Runnable myThreadAction() {
		for (int i = 0; i < 12; i++) {
			Double rdm = Math.random() * 250;
			long rdmLong = Math.round(rdm);
			try {
				Thread2Exo2.sleep(rdmLong);
				System.out.println(this.getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
	}
}
