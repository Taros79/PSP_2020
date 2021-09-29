package controllers;

import function.Actualizar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal_1 implements Initializable {

    @FXML
    private BorderPane pantallaPrincipal;

    @FXML
    private FXMLLoader fxmlLoaderCrearUsuarios;
    private AnchorPane pantallaCrearUsuarios;
    private CrearUsuarios crearUsuariosController;

    @FXML
    private FXMLLoader fxmlLoaderActualizarUsuarios;
    private AnchorPane pantallaActualizarUsuarios;
    private ActualizarUsuarios actualizarUsuariosController;

    @FXML
    private FXMLLoader fxmlLoaderBorrarUsuarios;
    private AnchorPane pantallaBorrarUsuarios;
    private BorrarUsuarios borrarUsuariosController;

    private Logger logger = Logger.getLogger(this.getClass().getName());


    @Inject
    public Principal_1(FXMLLoader fxmlLoaderCrearUsuarios, FXMLLoader fxmlLoaderActualizarUsuarios, FXMLLoader fxmlLoaderBorrarUsuarios) {
        this.fxmlLoaderCrearUsuarios = fxmlLoaderCrearUsuarios;
        this.fxmlLoaderActualizarUsuarios = fxmlLoaderActualizarUsuarios;
        this.fxmlLoaderBorrarUsuarios = fxmlLoaderBorrarUsuarios;
    }


    @FXML
    private void abrirCrearUsuario() {
        try {
            if (pantallaCrearUsuarios == null) {
                pantallaCrearUsuarios =
                        fxmlLoaderCrearUsuarios.load(getClass().getResourceAsStream("/fxml/crearUsuarios.fxml"));
                crearUsuariosController = fxmlLoaderCrearUsuarios.getController();
                crearUsuariosController.setBorderPane(this);
            }
            pantallaPrincipal.setCenter(pantallaCrearUsuarios);
            Actualizar.actualizarLView(crearUsuariosController.listaPersonas());
        } catch (IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void abrirActualizarUsuario() {
        try {
            if (pantallaActualizarUsuarios == null) {
                pantallaActualizarUsuarios =
                        fxmlLoaderActualizarUsuarios.load(getClass().getResourceAsStream("/fxml/actualizarUsuarios.fxml"));
                actualizarUsuariosController = fxmlLoaderActualizarUsuarios.getController();
                actualizarUsuariosController.setBorderPane(this);
            }
            pantallaPrincipal.setCenter(pantallaActualizarUsuarios);
            Actualizar.actualizarLView(actualizarUsuariosController.listaPersonas());
        } catch (IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void cargarBorrarUsuario() {
        try {
            if (pantallaBorrarUsuarios == null) {
                pantallaBorrarUsuarios =
                        fxmlLoaderBorrarUsuarios.load(getClass().getResourceAsStream("/fxml/borrarUsuarios.fxml"));
                borrarUsuariosController = fxmlLoaderBorrarUsuarios.getController();
                borrarUsuariosController.setBorderPane(this);
            }
            pantallaPrincipal.setCenter(pantallaBorrarUsuarios);
            Actualizar.actualizarLView(borrarUsuariosController.listaPersonas());
        } catch (IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
