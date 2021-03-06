package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loaderMenu = new FXMLLoader(
                getClass().getResource("/fxml/pantallaPrincipal.fxml"));
        BorderPane root = loaderMenu.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Pokemone");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}
