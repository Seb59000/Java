import java.sql.*; // importation des classes jdbc public class Class1 { public static void main (String[] args)

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("pilote chargé.");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		update();
		try {
			String url = "jdbc:mysql://localhost:3306/aviation";
			Connection con = DriverManager.getConnection(url, "root", "root");
			System.out.println("Connexion BDD.");

			Statement stm = con.createStatement(); // création d'un objet
													// requête directe

			ResultSet Resultat; // création d'une variable qui référencera
								// l'ensemble des résultats

			Resultat = stm.executeQuery("select * from pilote");
			// exécution de la requête

			// Exploitation du résultat de la requête
			while (Resultat.next()) {
				String i = Resultat.getString("nom");
				String str = Resultat.getString("brevet");
				Float f = Resultat.getFloat("nbHVol");
				System.out.println(i + " " + str + "  " + f);
			}

			Resultat.close(); // il est recommandé de fermer le resultset
								// même si le garbage collector fait le travail
								// quand même.
			con.close(); // idem pour la connexion

		} catch (SQLException e) {
			System.out.println("ça marche pas");
		}
	}

	private static void update() {
		try {
			// établissement de la connexion au lien JDBC
			String url = "jdbc:mysql://localhost:3306/aviation";
			Connection con = DriverManager.getConnection(url, "root", "root");

			// création d'un objet de la classe Statement qui permet
			// d'effectuer des requêtes liées à la connexion 'con'
			Statement stm = con.createStatement();
			// appel de la méthode executeUpdate de la classe
			// Statement qui permet d'écrire dans une base
			stm.executeUpdate(
					"INSERT INTO pilote (brevet, nom, pseudo, nbHVol, compa) VALUES ('PL-22','Test9','Test9', 4500 ,'AF')");
			// il est recommandé de fermer l'objet Statement
			stm.close();
			// et de fermer la connexion
			con.close();

		} catch (Exception e) {
			// ceci permet d'écrire l'exception interceptée
			e.printStackTrace();

		}

	}
}
