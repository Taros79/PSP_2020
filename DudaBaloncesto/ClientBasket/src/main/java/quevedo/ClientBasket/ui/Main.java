package quevedo.ClientBasket.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quevedo.ClientBasket.utils.ConstantesJavaFx;
import quevedo.ClientBasket.utils.ConstantesMensajes;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

public class Main {


    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(ConstantesJavaFx.FXML_MAIN_SCREEN_FXML));
            stage.setScene(new Scene(fxmlParent, 950, 650));
            stage.setTitle(ConstantesMensajes.LIGA_BASKET);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


}
