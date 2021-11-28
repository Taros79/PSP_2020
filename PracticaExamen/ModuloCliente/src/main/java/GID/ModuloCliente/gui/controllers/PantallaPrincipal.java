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

    private final FXMLLoader fxmlLoaderCasamientos;
    private AnchorPane pantallaCasamientos;
    private Casamientos casamientosController;

    @Inject
    public PantallaPrincipal(FXMLLoader fxmlLoaderSalidasYEntradas, FXMLLoader fxmlLoaderCasamientos) {
        this.fxmlLoaderSalidasYEntradas = fxmlLoaderSalidasYEntradas;
        this.fxmlLoaderCasamientos = fxmlLoaderCasamientos;
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

    @FXML
    private void casamientos() {
        if (pantallaCasamientos == null) {
            try {
                pantallaCasamientos = fxmlLoaderCasamientos.load(getClass()
                        .getResourceAsStream("/fxml/casamientos.fxml"));
                casamientosController = fxmlLoaderCasamientos.getController();
                casamientosController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        casamientosController.actualizar();
        pantallaPrincipal.setCenter(pantallaCasamientos);
    }
}
