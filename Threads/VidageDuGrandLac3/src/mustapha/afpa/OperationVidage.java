package mustapha.afpa;

public class OperationVidage {

	public static void main(String[] args) {

		Lac.Volume = 1000;
		Fleuve lac = new Fleuve(50);
		Pompe pompe = new Pompe(40);

		lac.setPriority(3);
		pompe.setPriority(8);

		lac.start();
		pompe.start();

	}

}
