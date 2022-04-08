package rol.Cliente.gui;

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
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(ConstantesGUI.FXML_FXMLPRINCIPAL_FXML));
            stage.setScene(new Scene(fxmlParent, 950, 650));
            stage.setTitle(ConstantesGUI.TFG_CHARLESTON);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


}
