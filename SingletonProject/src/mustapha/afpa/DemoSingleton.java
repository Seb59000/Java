package mustapha.afpa;

public class DemoSingleton {

	public static void main(String[] args) {

		// illegal construct
		// Compile Time Error: The constructor UniqueObj() is not visible

		// UniqueObj object = new UniqueObj();

		// accès à la seule instance disponible
		UniqueObj object1 = UniqueObj.getInstance();
		// affiche compteur
		object1.showCounter();
		UniqueObj object2 = UniqueObj.getInstance();
		object2.showCounter();
		
		System.out.println("egalité des objets : " + (object1 == object2));

	}
}