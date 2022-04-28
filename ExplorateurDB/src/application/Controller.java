package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import java.sql.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class Controller implements Initializable {

	private ArrayList<ArrayList<String>> dataArray = new ArrayList<>();

	@FXML
	private ComboBox<String> cbServeurDB;

	@FXML
	private TextField tfIPServeur;

	@FXML
	private TextField tfPort;

	@FXML
	private ListView<String> lvBDD;

	@FXML
	private ListView<String> lvTables;

	@FXML
	private TableView<ObservableList<String>> tvContenu;

	@FXML
	void charger() {

	}

	@FXML
	void connexion() {
		showConnectionDialog();
		if (Parametres.infosPresentes) {
			Parametres.IPServeur = tfIPServeur.getText();
			Parametres.Port = tfPort.getText();

			Connection conn = (Connection) SingletonConnexion.getInstance();
			remplirListViewBDD(conn);
		}
	}

	private ObservableList<ObservableList<String>> buildData(ArrayList<ArrayList<String>> dataArray) {
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		for (ArrayList<String> row : dataArray) {
			data.add(FXCollections.observableArrayList(row));
		}

		return data;
	}

	private TableView<ObservableList<String>> createTableView(ArrayList<ArrayList<String>> dataArray) {
		TableView<ObservableList<String>> tableView = new TableView<>();
		tableView.setItems(buildData(dataArray));

		for (int i = 0; i < dataArray.get(0).size(); i++) {
			final int curCol = i;
			final TableColumn<ObservableList<String>, String> column = new TableColumn<>("Col " + (curCol + 1));
			column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol)));
			tableView.getColumns().add(column);
		}
		return tableView;
	}

	private void addListenerToListViewTables() {

		lvTables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@SuppressWarnings({ "rawtypes"})
			@Override
			public void changed(ObservableValue ov, String t, String t1) {

				if (!Parametres.premiereEtape) {
					creerColonnesCorrespondantes(t1);
					ObservableList<String> o = FXCollections.observableArrayList();
					ObservableList<ObservableList<String>> o2 = FXCollections.observableArrayList();

					Connection conn = (Connection) SingletonConnexion.getInstance();
					try {
						Statement stm = conn.createStatement();
						ResultSet Resultat;
						Resultat = stm.executeQuery("select * from " + t1.toString());
						System.out.println("select * from " + t1.toString());

						while (Resultat.next()) {
							for (int i = 0; i < Parametres.typeDesColonnes.size(); i++) {
								ArrayList<String> row = new ArrayList<>();
								System.out.println("Nom " + Parametres.typeDesColonnes.get(i).get_nom());
								System.out.println("Type " + Parametres.typeDesColonnes.get(i).get_type());

								if (Parametres.typeDesColonnes.get(i).get_type().contains("char")) {
									System.out.println("char detectée.");
									System.out.println("Resultat "
											+ Resultat.getString(Parametres.typeDesColonnes.get(i).get_nom()));
									row.add(Resultat.getString(Parametres.typeDesColonnes.get(i).get_nom()));
									o.add(Resultat.getString(Parametres.typeDesColonnes.get(i).get_nom()));
								}
								if (Parametres.typeDesColonnes.get(i).get_type().contains("int")) {
									System.out.println("int detectée.");
									System.out.println(
											"Resultat " + Resultat.getInt(Parametres.typeDesColonnes.get(i).get_nom()));
									Integer intTemp = Resultat.getInt(Parametres.typeDesColonnes.get(i).get_nom());
									String stringTemp = intTemp.toString();
									o.add(stringTemp);
									row.add(stringTemp);
								}
								if (Parametres.typeDesColonnes.get(i).get_type().contains("decimal")) {
									System.out.println("float detectée.");
									System.out.println("Resultat "
											+ Resultat.getFloat(Parametres.typeDesColonnes.get(i).get_nom()));
									Float floatTemp = Resultat.getFloat(Parametres.typeDesColonnes.get(i).get_nom());
									String stringTemp = floatTemp.toString();
									row.add(stringTemp);
									o.add(stringTemp);
								}
								if (Parametres.typeDesColonnes.get(i).get_type().contains("datetime")) {
									System.out.println("datetime detectée.");
								} else if (Parametres.typeDesColonnes.get(i).get_type().contains("date")) {
									System.out.println("date detectée.");
								} else if (Parametres.typeDesColonnes.get(i).get_type().contains("time")) {
									System.out.println("time detectée.");
								}
//								dataArray.add(row);
							}
							System.out.println("Ligne ");
//							 o2.add(o);
							// dataArray.add(row);
						}
//						TableView<ObservableList<String>> tableView = createTableView(dataArray);
//						tableView.getClass().toString();
//						tvContenu = tableView;
						tvContenu.setItems(o2);
						stm.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}


	private void creerColonnesCorrespondantes(String table) {
		ObservableList<String> o = FXCollections.observableArrayList();
		Connection conn = (Connection) SingletonConnexion.getInstance();
		tvContenu.getColumns().clear();
		try {
			Statement stm = conn.createStatement();
			ResultSet Resultat;
			Resultat = stm.executeQuery("describe " + table.toString());
			Parametres.typeDesColonnes.clear();
			tvContenu.getColumns().clear();
			while (Resultat.next()) {
				Parametres.typeDesColonnes.add(new Colonne(Resultat.getString("field"), Resultat.getString("type")));
				o.add(Resultat.getString("field"));
				TableColumn col = new TableColumn(Resultat.getString("field"));
				tvContenu.getColumns().add(col);
			}
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void addListenerToListViewBDD() {

		lvBDD.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				Parametres.premiereEtape = true;
				ObservableList<String> o = FXCollections.observableArrayList();
				Parametres.BDD = t1;
				Connection conn = (Connection) SingletonConnexion.relancerConnexion();
				try {
					DatabaseMetaData dbmd = conn.getMetaData();
					String[] types = { "TABLE" };
					ResultSet rs = dbmd.getTables(null, null, "%", types);
					while (rs.next()) {
						o.add(rs.getString("TABLE_NAME"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				lvTables.setItems(o);
				Parametres.premiereEtape = false;
			}
		});
	}

	private void remplirListViewBDD(Connection conn) {
		ObservableList<String> o = FXCollections.observableArrayList();

		try {
			Statement stm = conn.createStatement();
			ResultSet Resultat;

			Resultat = stm.executeQuery("show databases");

			while (Resultat.next()) {
				o.add(Resultat.getString("database"));
			}
			Resultat.close();
			lvBDD.setItems(o);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showConnectionDialog() {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Connexion");
		dialog.setHeaderText("Veuillez entrer votre identifiant et votre mot de passe.");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Connexion", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Utilisateur");
		PasswordField password = new PasswordField();
		password.setPromptText("Mot de passe");

		grid.add(new Label("Utilisateur:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Mot de passe:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was
		// entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button
		// is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			Parametres.userName = usernamePassword.getKey();
			Parametres.password = usernamePassword.getValue();
			Parametres.infosPresentes = true;
		});
	}

	@FXML
	void executer() {

	}

	@FXML
	void sauver() {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		remplirComboboxServeurDB();
		addListenerToComboboxServeurDB();
		addListenerToListViewBDD();
		addListenerToListViewTables();
	}

	private void addListenerToComboboxServeurDB() {
		cbServeurDB.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				if (t1.toString() != "MySQL") {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Fonction Non Implémentée pour l'instant.");
					alert.showAndWait();
				}
			}
		});
	}

	private void remplirComboboxServeurDB() {
		ObservableList<String> o = FXCollections.observableArrayList();
		o.add("MySQL");
		o.add("Oracle");
		o.add("MSSQL");
		o.add("Derby");
		cbServeurDB.setItems(o);
		cbServeurDB.getSelectionModel().selectFirst();
	}

}
