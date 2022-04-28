import java.sql.*; // importation des classes jdbc public class Class1 { public static void main (String[] args)

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("pilote charg�.");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		update();
		try {
			String url = "jdbc:mysql://localhost:3306/aviation";
			Connection con = DriverManager.getConnection(url, "root", "root");
			System.out.println("Connexion BDD.");

			Statement stm = con.createStatement(); // cr�ation d'un objet
													// requ�te directe

			ResultSet Resultat; // cr�ation d'une variable qui r�f�rencera
								// l'ensemble des r�sultats

			Resultat = stm.executeQuery("select * from pilote");
			// ex�cution de la requ�te

			// Exploitation du r�sultat de la requ�te
			while (Resultat.next()) {
				String i = Resultat.getString("nom");
				String str = Resultat.getString("brevet");
				Float f = Resultat.getFloat("nbHVol");
				System.out.println(i + " " + str + "  " + f);
			}

			Resultat.close(); // il est recommand� de fermer le resultset
								// m�me si le garbage collector fait le travail
								// quand m�me.
			con.close(); // idem pour la connexion

		} catch (SQLException e) {
			System.out.println("�a marche pas");
		}
	}

	private static void update() {
		try {
			// �tablissement de la connexion au lien JDBC
			String url = "jdbc:mysql://localhost:3306/aviation";
			Connection con = DriverManager.getConnection(url, "root", "root");

			// cr�ation d'un objet de la classe Statement qui permet
			// d'effectuer des requ�tes li�es � la connexion 'con'
			Statement stm = con.createStatement();
			// appel de la m�thode executeUpdate de la classe
			// Statement qui permet d'�crire dans une base
			stm.executeUpdate(
					"INSERT INTO pilote (brevet, nom, pseudo, nbHVol, compa) VALUES ('PL-22','Test9','Test9', 4500 ,'AF')");
			// il est recommand� de fermer l'objet Statement
			stm.close();
			// et de fermer la connexion
			con.close();

		} catch (Exception e) {
			// ceci permet d'�crire l'exception intercept�e
			e.printStackTrace();

		}

	}
}
