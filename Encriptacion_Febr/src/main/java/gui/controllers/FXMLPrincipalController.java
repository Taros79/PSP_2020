package gui.controllers;

import gui.utils.ConstantesGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class FXMLPrincipalController implements Initializable {

    private final FXMLLoader fxmlLoaderSecretitos;
    @FXML
    private BorderPane pantallaPrincipal;
    private AnchorPane pantallaSecretitos;
    private Secretitos secretitosController;

    @Inject
    public FXMLPrincipalController(FXMLLoader fxmlLoaderSecretitos) {
        this.fxmlLoaderSecretitos = fxmlLoaderSecretitos;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadSecretitos();
    }


    @FXML
    private void preloadSecretitos() {
        if (pantallaSecretitos == null) {
            try {
                pantallaSecretitos = fxmlLoaderSecretitos.load(getClass()
                        .getResourceAsStream(ConstantesGUI.ROUTE_FXML_SECRETS));
                secretitosController = fxmlLoaderSecretitos.getController();
                secretitosController.setPantallaPrincipal(this);
                pantallaPrincipal.setCenter(pantallaSecretitos);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

}