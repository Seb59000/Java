package mustapha.afpa;

public class UniqueObj {


	// création de l'objet
	private static UniqueObj instance = new UniqueObj();
	private static int nbreAppel = 0;


	// Constructeur privé : on ne peut pas créer des instances de cette classe
	private UniqueObj() {
	}

	// accès au seul objet existant
	public static UniqueObj getInstance() {
		nbreAppel++;
		return instance;
	}

	public void showCounter() {
		System.out.println("Vous m'avez appelé : " +nbreAppel + " fois");
	}
}