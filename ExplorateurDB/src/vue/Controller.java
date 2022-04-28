package vue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import com.mysql.jdbc.Connection;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Pair;
import model.Parametres;
import model.SingletonConnexion;

public class Controller implements Initializable {

	// TABLE VIEW AND DATA
	private ObservableList<ObservableList<String>> data;

	@FXML
	private ComboBox<String> cbServeurDB;

	@FXML
	private TextField tfIPServeur;

	@FXML
	private TextField tfPort;

	@FXML
	private TextArea tARequete;

	@FXML
	private ListView<String> lvBDD;

	@FXML
	private ListView<String> lvTables;

	@FXML
	private TableView<ObservableList<String>> tvContenu;

	@FXML
	void charger() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("amsql files (*.amsql)", "*.amsql");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showOpenDialog(Parametres.primaryStage);
		if (file != null) {
			tARequete.setText(readFile(file));
		}
	}

	private String readFile(File file) {
		StringBuilder stringBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {

			bufferedReader = new BufferedReader(new FileReader(file));

			String text;
			while ((text = bufferedReader.readLine()) != null) {
				stringBuffer.append(text);
			}

		} catch (FileNotFoundException e) {
			messageErreur(e.getMessage());
		} catch (IOException e) {
			messageErreur(e.getMessage());
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				messageErreur(e.getMessage());
			}
		}

		return stringBuffer.toString();
	}

	@FXML
	void connexion() {
		showConnectionDialog();
		if (Parametres.infosPresentes) {
			Parametres.IPServeur = tfIPServeur.getText();
			Parametres.Port = tfPort.getText();
			Connection conn = (Connection) SingletonConnexion.getInstance();

			if (conn != null) {
				remplirListViewBDD(conn);
			}

		}
	}

	private void addListenerToListViewTables() {

		lvTables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void changed(ObservableValue ov, String t, String t1) {

				if (!Parametres.premiereEtape) {
					data = FXCollections.observableArrayList();
					data.clear();
					tvContenu.getColumns().clear();
					Connection conn = (Connection) SingletonConnexion.getInstance();
					try {
						Statement stm = conn.createStatement();
						ResultSet Resultat;
						Resultat = stm.executeQuery("select * from " + t1.toString());
						/**********************************
						 * TABLE COLUMN ADDED DYNAMICALLY *
						 **********************************/
						for (int i = 0; i < Resultat.getMetaData().getColumnCount(); i++) {
							final int j = i;
							TableColumn col = new TableColumn(Resultat.getMetaData().getColumnName(i + 1));
							col.setCellValueFactory(
									new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
										public ObservableValue<String> call(
												CellDataFeatures<ObservableList, String> param) {
											return new SimpleStringProperty(param.getValue().get(j).toString());
										}
									});
							tvContenu.getColumns().addAll(col);
						}
						/********************************
						 * Data added to ObservableList *
						 ********************************/
						while (Resultat.next()) {
							// Iterate Row
							ObservableList<String> row = FXCollections.observableArrayList();
							for (int i = 1; i <= Resultat.getMetaData().getColumnCount(); i++) {
								// Iterate Column
								if (Resultat.getString(i) == null) {
									row.add("null");
								} else
									row.add(Resultat.getString(i));
							}
							data.add(row);
						}
						tvContenu.setItems(data);
						stm.close();
					} catch (SQLException e) {
						messageErreur(e.getMessage());
					}
				}
			}
		});
	}

	private void addListenerToListViewBDD() {

		lvBDD.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@SuppressWarnings("rawtypes")
			@Override
			public void changed(ObservableValue ov, String arg1, String table) {
				Parametres.premiereEtape = true;
				try {
					ObservableList<String> o = FXCollections.observableArrayList();
					Parametres.BDD = table;
					Connection conn = (Connection) SingletonConnexion.relancerConnexion();
					conn.createStatement().executeQuery("USE " + table + ";");
					Statement stm = conn.createStatement();
					ResultSet Resultat;

					Resultat = stm.executeQuery("show tables");
					while (Resultat.next()) {
						o.add(Resultat.getString(1));
					}
					lvTables.setItems(o);
					Parametres.premiereEtape = false;
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
			messageErreur(e.getMessage());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	void executer() {
		if (!Parametres.premiereEtape) {
			data = FXCollections.observableArrayList();
			data.clear();
			tvContenu.getColumns().clear();
			Connection conn = (Connection) SingletonConnexion.getInstance();
			String requeteSQL = tARequete.getText();
			try {
				Statement stm = conn.createStatement();
				if (requeteSQL.trim().startsWith("d") | requeteSQL.trim().startsWith("D")
						| requeteSQL.trim().startsWith("c") | requeteSQL.trim().startsWith("C")
						| requeteSQL.trim().startsWith("a") | requeteSQL.trim().startsWith("A")
						| requeteSQL.trim().startsWith("del") | requeteSQL.trim().startsWith("DEL")
						| requeteSQL.trim().startsWith("Del") | requeteSQL.trim().startsWith("i")
						| requeteSQL.trim().startsWith("I") | requeteSQL.trim().startsWith("u")
						| requeteSQL.trim().startsWith("U")) {
					stm.execute(requeteSQL);
				} else if (requeteSQL.trim().startsWith("s") | requeteSQL.trim().startsWith("S")
						| requeteSQL.trim().startsWith("des") | requeteSQL.trim().startsWith("DES")
						| requeteSQL.trim().startsWith("Des")) {
					ResultSet Resultat;
					Resultat = stm.executeQuery(requeteSQL);
					/**********************************
					 * TABLE COLUMN ADDED DYNAMICALLY *
					 **********************************/
					for (int i = 0; i < Resultat.getMetaData().getColumnCount(); i++) {
						final int j = i;
						TableColumn col = new TableColumn(Resultat.getMetaData().getColumnName(i + 1));
						col.setCellValueFactory(
								new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
									public ObservableValue<String> call(
											CellDataFeatures<ObservableList, String> param) {
										return new SimpleStringProperty(param.getValue().get(j).toString());
									}
								});
						tvContenu.getColumns().addAll(col);
					}
					/********************************
					 * Data ajouté à ObservableList *
					 ********************************/
					while (Resultat.next()) {
						// Iterate Row
						ObservableList<String> row = FXCollections.observableArrayList();
						for (int i = 1; i <= Resultat.getMetaData().getColumnCount(); i++) {
							if (Resultat.getString(i) == null) {
								row.add("null");
							} else
								row.add(Resultat.getString(i));
						}
						data.add(row);
					}
					// FINALLY ADDED TO TableView
					tvContenu.setItems(data);
				} else
					messageErreur("Format de la requête non reconnu.");

				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
				messageErreur(e.getMessage());
			}
		} else
			messageErreur("Veuillez sélectionner une base de données.");
	}

	private void messageErreur(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	void sauver() {
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("amsql files (*.amsql)", "*.amsql");
		fileChooser.getExtensionFilters().add(extFilter);
		// Show save file dialog
		File file = fileChooser.showSaveDialog(Parametres.primaryStage);
		if (file != null) {
			SaveFile(tARequete.getText(), file);
		}
	}

	private void SaveFile(String content, File file) {
		try {
			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException e) {
			messageErreur(e.getMessage());
		}
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
			@SuppressWarnings("rawtypes")
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
