package GID.ModuloCliente.gui.controllers;

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
public class PantallaPrincipal implements Initializable {

    @FXML
    private BorderPane pantallaPrincipal;

    private final FXMLLoader fxmlLoaderSalidasYEntradas;
    private AnchorPane pantallaSalidasYEntradas;
    private SalidasYEntradas salidasYEntradasController;

    @Inject
    public PantallaPrincipal(FXMLLoader fxmlLoaderSalidasYEntradas) {
        this.fxmlLoaderSalidasYEntradas = fxmlLoaderSalidasYEntradas;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void salidasYEntradas() {
        if (pantallaSalidasYEntradas == null) {
            try {
                pantallaSalidasYEntradas = fxmlLoaderSalidasYEntradas.load(getClass()
                        .getResourceAsStream("/fxml/salidasYEntradas.fxml"));
                salidasYEntradasController = fxmlLoaderSalidasYEntradas.getController();
                salidasYEntradasController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        salidasYEntradasController.actualizar();
        pantallaPrincipal.setCenter(pantallaSalidasYEntradas);
    }

}
