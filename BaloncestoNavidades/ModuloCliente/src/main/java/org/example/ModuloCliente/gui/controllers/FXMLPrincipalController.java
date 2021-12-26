package org.example.ModuloCliente.gui.controllers;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class FXMLPrincipalController implements Initializable {

    @FXML
    private BorderPane pantallaPrincipal;


    private final FXMLLoader fxmlLoaderIniciarSesion;
    private AnchorPane pantallaIniciarSesion;
    private IniciarSesion iniciarSesionController;

    @Inject
    public FXMLPrincipalController(FXMLLoader fxmlLoaderIniciarSesion) {
        this.fxmlLoaderIniciarSesion = fxmlLoaderIniciarSesion;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @FXML
    private void iniciarSesion() {
        if (pantallaIniciarSesion == null) {
            try {
                pantallaIniciarSesion = fxmlLoaderIniciarSesion.load(getClass()
                        .getResourceAsStream("/fxml/iniciarSesion.fxml"));
                iniciarSesionController = fxmlLoaderIniciarSesion.getController();
                iniciarSesionController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        pantallaPrincipal.setCenter(pantallaIniciarSesion);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
