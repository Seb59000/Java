package application;

import java.sql.*;

public class SingletonConnexion {

	private static final String url = "jdbc:mysql://";
	private static Connection con;

	private SingletonConnexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(url + Parametres.IPServeur + ":" + Parametres.Port + "/" + Parametres.BDD,
					Parametres.userName, Parametres.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getInstance() {
		if (con == null) {
			SingletonConnexion connexion = new SingletonConnexion();
			System.out.println("connexion");
		}
		return con;
	}

	public static Connection relancerConnexion() {
		SingletonConnexion connexion = new SingletonConnexion();
		return con;
	}
}
