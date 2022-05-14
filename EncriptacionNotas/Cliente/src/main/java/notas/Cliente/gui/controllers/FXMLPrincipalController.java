package notas.Cliente.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import notas.Cliente.dao.utils.CacheAuthorization;
import notas.Cliente.gui.ConstantesGUI;
import notas.Cliente.gui.controllers.complements.*;
import notas.Common.modelo.Usuario;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class FXMLPrincipalController implements Initializable {

    private final FXMLLoader fxmlLoaderIniciarSesion;
    private final FXMLLoader fxmlLoaderPerfil;

    @FXML
    private Menu menuAdmin;
    @FXML
    private Menu menuUsuario;
    @FXML
    private MenuItem menuItemRegistro;

    @FXML
    private BorderPane pantallaPrincipal;

    private AnchorPane pantallaIniciarSesion;
    private IniciarSesion iniciarSesionController;
    private AnchorPane pantallaPerfil;
    private Perfil perfilController;

    private Usuario usuarioLogin;
    private CacheAuthorization ca;


    @Inject
    public FXMLPrincipalController(FXMLLoader fxmlLoaderIniciarSesion, FXMLLoader fxmlLoaderPerfil,
                                   CacheAuthorization ca) {
        this.fxmlLoaderIniciarSesion = fxmlLoaderIniciarSesion;
        this.fxmlLoaderPerfil = fxmlLoaderPerfil;
        this.ca = ca;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadIniciarSesion();
        preloadPerfil();
    }

    public Usuario getUsuarioLoginPrincipal() {
        return usuarioLogin;
    }

    public void setUsuarioLoginPrincipal(Usuario usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    private void preloadIniciarSesion() {
        if (pantallaIniciarSesion == null) {
            try {
                pantallaIniciarSesion = fxmlLoaderIniciarSesion.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_INICIAR_SESION_FXML));
                iniciarSesionController = fxmlLoaderIniciarSesion.getController();
                iniciarSesionController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void preloadPerfil() {
        if (pantallaPerfil == null) {
            try {
                pantallaPerfil = fxmlLoaderPerfil.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_PERFIL_FXML));
                perfilController = fxmlLoaderPerfil.getController();
                perfilController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @FXML
    private void perfil() {
        pantallaPrincipal.setCenter(pantallaPerfil);
        perfilController.actualizarDatos();
    }

    @FXML
    private void iniciarSesion() {
        if (ca.getNombre() != null) {
            ca.setNombre(null);
            ca.setContraseña(null);
            ca.setToken(null);
        }
        pantallaPrincipal.setCenter(pantallaIniciarSesion);
    }

    @FXML
    private void logout() {
        //serviciosUsuario.hacerLogout();
        pantallaPrincipal.setCenter(pantallaIniciarSesion);
        if (menuUsuario.isVisible()) {
            menuUsuario.setVisible(false);
        }
        if (menuAdmin.isVisible()) {
            menuAdmin.setVisible(false);
        }
        if (!menuItemRegistro.isVisible()) {
            menuItemRegistro.setVisible(true);
        }
        if (ca.getNombre() != null) {
            ca.setNombre(null);
            ca.setContraseña(null);
            ca.setToken(null);
        }
    }

    //----------------------------------

    public void irAPrincipalAdmin() {
        pantallaPrincipal.setCenter(pantallaPerfil);
        menuUsuario.setVisible(true);
        menuAdmin.setVisible(true);
        menuItemRegistro.setVisible(false);
    }

    public void irAPrincipalUsuario() {
        pantallaPrincipal.setCenter(pantallaPerfil);
        pantallaPrincipal.setVisible(true);
        menuUsuario.setVisible(true);
        menuItemRegistro.setVisible(false);
    }
}