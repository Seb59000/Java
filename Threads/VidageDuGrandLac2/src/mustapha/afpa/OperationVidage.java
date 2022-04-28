package mustapha.afpa;

public class OperationVidage {

	public static void main(String[] args) {

		Lac.Volume = 1000;
		Fleuve lac = new Fleuve(50);
		Pompe pompe = new Pompe(45);

		lac.start();
		pompe.start();

	}

}
