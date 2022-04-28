package model;

import java.sql.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SingletonConnexion {

	private static final String url = "jdbc:mysql://";
	private static Connection con;

	private SingletonConnexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			messageErreur(e.getMessage());
		}
		try {
			con = DriverManager.getConnection(
					url + Parametres.IPServeur + ":" + Parametres.Port + "/" + Parametres.BDD + "?useSSL=false",
					Parametres.userName, Parametres.password);
		} catch (SQLException e) {
			// e.printStackTrace();
			messageErreur(e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	public static Connection getInstance() {
		if (con == null) {
			SingletonConnexion connexion = new SingletonConnexion();
		}
		return con;
	}

	@SuppressWarnings("unused")
	public static Connection relancerConnexion() {
		SingletonConnexion connexion = new SingletonConnexion();
		return con;
	}

	private void messageErreur(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();

	}
}
