
public class Model {


	public Model() {
		
	}

	public boolean prime(int nb) {
		boolean isNbPremier = true;

		for (int i = nb - 1; i > 1; i--) {
			if (nb % i == 0) {
				isNbPremier = false;
			}
		}
		return isNbPremier;
	}
}
