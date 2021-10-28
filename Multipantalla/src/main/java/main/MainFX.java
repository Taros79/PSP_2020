package main;

import gui.StartupScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

public class MainFX {
    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream("/fxml/principal_1.fxml"));
            stage.setScene(new Scene(fxmlParent, 300, 100));
            stage.setTitle("Hello World FXML JavaFX");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            //System.exit(0);
        }
    }

}
