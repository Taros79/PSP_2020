package org.example.ModuloCliente.gui.controllers;

import javafx.event.ActionEvent;
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

    private final FXMLLoader fxmlLoaderRegistrarUsuario;
    private AnchorPane pantallaRegistrarUsuario;
    private RegistrarUsuario registrarUsuarioController;

    @Inject
    public FXMLPrincipalController(FXMLLoader fxmlLoaderIniciarSesion, FXMLLoader fxmlLoaderRegistrarUsuario) {
        this.fxmlLoaderIniciarSesion = fxmlLoaderIniciarSesion;
        this.fxmlLoaderRegistrarUsuario = fxmlLoaderRegistrarUsuario;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadIniciarSesion();
        preloadRegistrarUsuario();
    }

    @FXML
    private void preloadIniciarSesion() {
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
    }

    @FXML
    private void preloadRegistrarUsuario() {
        if (pantallaRegistrarUsuario == null) {
            try {
                pantallaRegistrarUsuario = fxmlLoaderRegistrarUsuario.load(getClass()
                        .getResourceAsStream("/fxml/registrarUsuario.fxml"));
                registrarUsuarioController = fxmlLoaderRegistrarUsuario.getController();
                registrarUsuarioController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void iniciarSesion() {
        pantallaPrincipal.setCenter(pantallaIniciarSesion);
    }

    public void registrarUsuario() {
        pantallaPrincipal.setCenter(pantallaRegistrarUsuario);
    }

}
