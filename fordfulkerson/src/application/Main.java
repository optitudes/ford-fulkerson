package application;
	
import java.io.IOException;

import application.controller.PrincipalViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			//Cambia el titulo del escenario
			this.primaryStage.setTitle("Ford-Fulkerson");
			mostrarVentanaPrincipal();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		/*
	 * Método que carga el archivo fxml, la escena y el controlador para hacer referencia al main
	 */
	private void mostrarVentanaPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/PrincipalView.fxml"));
			AnchorPane rootLayout = (AnchorPane)loader.load();
			PrincipalViewController controller = loader.getController();
			controller.setMain(this);
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
