package org.example.ModuloCliente.gui.controllers;

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


    private final FXMLLoader fxmlLoaderIniciarSesion;
    private AnchorPane pantallaIniciarSesion;
    private IniciarSesion iniciarSesionController;

    private final FXMLLoader fxmlLoaderCrearUsuario;
    private AnchorPane pantallaCrearUsuario;
    private CrearUsuario crearUsuarioController;

    @Inject
    public FXMLPrincipalController(FXMLLoader fxmlLoaderIniciarSesion, FXMLLoader fxmlLoaderCrearUsuario) {
        this.fxmlLoaderIniciarSesion = fxmlLoaderIniciarSesion;
        this.fxmlLoaderCrearUsuario = fxmlLoaderCrearUsuario;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadCrearUsuario();
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

    @FXML
    private void preloadCrearUsuario() {
        if (pantallaCrearUsuario == null) {
            try {
                pantallaCrearUsuario = fxmlLoaderCrearUsuario.load(getClass()
                        .getResourceAsStream("/fxml/crearUsuario.fxml"));
                crearUsuarioController = fxmlLoaderCrearUsuario.getController();
                crearUsuarioController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void crearUsuario() {
        pantallaPrincipal.setCenter(pantallaCrearUsuario);
    }
}
