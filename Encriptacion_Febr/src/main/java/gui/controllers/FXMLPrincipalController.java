package gui.controllers;


import config.Configuration;
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

    @FXML
    private BorderPane pantallaPrincipal;

    private final FXMLLoader fxmlLoaderResultadoPartidos;
    private AnchorPane pantallaResultadoPartidos;
    private AdministracionPartidos administracionPartidosController;

    private final Configuration configYaml;

    @Inject
    public FXMLPrincipalController(FXMLLoader fxmlLoaderResultadoPartidos, Configuration configYaml){
        this.fxmlLoaderResultadoPartidos = fxmlLoaderResultadoPartidos;
        this.configYaml = configYaml;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configYaml.cargar();
        preloadResultadoPartidos();
    }


    @FXML
    private void preloadResultadoPartidos() {
        if (pantallaResultadoPartidos == null) {
            try {
                pantallaResultadoPartidos = fxmlLoaderResultadoPartidos.load(getClass()
                        .getResourceAsStream("/fxml/administracionPartidos.fxml"));
                administracionPartidosController = fxmlLoaderResultadoPartidos.getController();
                administracionPartidosController.setPantallaPrincipal(this);
                pantallaPrincipal.setCenter(pantallaResultadoPartidos);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

}