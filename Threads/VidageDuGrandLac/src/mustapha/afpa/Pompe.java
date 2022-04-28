package mustapha.afpa;

public class Pompe {

	private int qp;

	public Pompe(int qp) {
		this.qp = qp;
	}

	public void pompage() {

		for (int i = 1; i <= 365; i++) {
			if (Lac.Volume >= Lac.seuilCritique) {
				System.out.println("Le lac est d�j� submerg�. on abondonne..");
				System.exit(-1);
			}
			if (Lac.Volume <= 0) {
				System.out.println("Le lac est assech�.. Hourra on arr�te tout.");
				System.exit(0);
			} else {
				Lac.Volume -= this.qp;
				System.out.println("Pompage(" + i + ") : La quantit� d'eau dans le lac est :" + Lac.Volume);
			}
		}
	}

}
