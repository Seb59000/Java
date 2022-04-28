package mustapha.afpa;

public class Fleuve {

	private int apfv;

	public Fleuve(int apfv) {
		this.apfv = apfv;
	}

	public void apportFluvial() {

		for (int i = 1; i <= 365; i++) {
			if (Lac.Volume <= 0) {
				System.out.println("Lac asseché, on arrête tout....");
				System.exit(0);
			} else if (Lac.Volume >= Lac.seuilCritique) {
				System.out.println("Le lac deborde, barrez vous....");
				System.exit(-1);
			} else {
				Lac.Volume += this.apfv;
				System.out.println("Ajout(" + i + ") => La quantité d'eau dans le lac est : " + Lac.Volume);
			}
		}
	}
}
