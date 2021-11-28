package GID.ModuloCliente.gui.main;

import GID.ModuloCliente.gui.StartupScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

public class Main {

    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream("/fxml/pantallaPrincipal.fxml"));
            stage.setScene(new Scene(fxmlParent, 650, 500));
            stage.setTitle("API");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
