package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PantallaPrincipal implements Initializable {

    @FXML
    private MenuItem menuRegistrarse;
    @FXML
    private MenuItem menuIniciarSesion;
    @FXML
    private MenuItem menuSalir;
    @FXML
    private Menu menuMaster;
    @FXML
    private Menu menuUsuario;
    @FXML
    private BorderPane pantallaPrincipal;

    @FXML
    private FXMLLoader fxmlLoaderPerfilUsuario;
    private AnchorPane pantallaPerfilUsuario;
    private PerfilUsuario perfilUsuarioController;

    private Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

   /* @FXML
    private void perfilUsuario() {
        try {
            if (pantallaPerfilUsuario == null) {
                pantallaPerfilUsuario =
                        fxmlLoaderPerfilUsuario.load(getClass().getResourceAsStream("/fxml/perfilUsuario.fxml"));
                perfilUsuarioController = fxmlLoaderPerfilUsuario.getController();
                perfilUsuarioController.setBorderPane(this);
            }
            pantallaPrincipal.setCenter(pantallaPerfilUsuario);
        } catch (IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void abrirPerfil(ActionEvent actionEvent) {
        this.perfilUsuario();
    }*/

    @FXML
    private void perfilUsuario() {
        FXMLLoader loaderMenu = new FXMLLoader(
                getClass().getResource("/fxml/perfilUsuario.fxml"));
        try {
            if (pantallaPerfilUsuario == null) {
                pantallaPerfilUsuario = loaderMenu.load();
                perfilUsuarioController = loaderMenu.getController();
                perfilUsuarioController.setBorderPane(this);
            }
            pantallaPrincipal.setCenter(pantallaPerfilUsuario);
        } catch (IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

}
