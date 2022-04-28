package application;
	
import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.mainStage = primaryStage;
			primaryStage.setOnCloseRequest(confirmCloseEventHandler);
			AnchorPane root = FXMLLoader.load(getClass().getResource("/vue/View.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Etes vous sur de vouloir quitter l'application?"
        );
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );
        exitButton.setText("Quitter");
        closeConfirmation.setHeaderText("Confirmation");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        closeConfirmation.initOwner(mainStage);

        // normally, you would just use the default alert positioning,
        // but for this simple sample the main stage is small,
        // so explicitly position the alert so that the main window can still be seen.
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        mainStage.setX((primScreenBounds.getWidth() - mainStage.getWidth()) / 2);
//        mainStage.setY((primScreenBounds.getHeight() - mainStage.getHeight()) / 2);
//        closeConfirmation.setX(mainStage.getX());
//        closeConfirmation.setY(mainStage.getY() + mainStage.getHeight());

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
        }
    };

	public static void main(String[] args) {
		launch(args);
	}
}
