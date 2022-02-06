package gui;

import gui.utils.ConstantesGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

@Log4j2
public class MainFX {

    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlparent = fxmlLoader.load(getClass().getResourceAsStream(ConstantesGUI.FXML_FXMLPRINCIPAL_FXML));
            stage.setScene(new Scene(fxmlparent));
            stage.setTitle(ConstantesGUI.TITULO_PRINCIPAL);
            stage.show();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }


}
