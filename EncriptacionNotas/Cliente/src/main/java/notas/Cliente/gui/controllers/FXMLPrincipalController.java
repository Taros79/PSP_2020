package notas.Cliente.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import notas.Cliente.Servicios.ServiciosUsuario;
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
    private final FXMLLoader fxmlLoaderJefatura;
    private final FXMLLoader fxmlLoaderConsultasPadre;
    private final FXMLLoader fxmlLoaderPonerParte;


    @FXML
    private Menu menuPadre;
    @FXML
    private Menu menuProfe;
    @FXML
    private Menu menuJefatura;

    @FXML
    private BorderPane pantallaPrincipal;

    private AnchorPane pantallaIniciarSesion;
    private IniciarSesion iniciarSesionController;

    private AnchorPane pantallaPonerParte;
    private PonerParte ponerParteController;

    private AnchorPane pantallaJefatura;
    private Jefatura jefaturaController;

    private AnchorPane pantallaConsultasPadre;
    private ConsultasPadre consultasPadreController;

    private Usuario usuarioLogin;
    private ServiciosUsuario serviciosUsuario;
    private CacheAuthorization ca;


    @Inject
    public FXMLPrincipalController(ServiciosUsuario serviciosUsuario, FXMLLoader fxmlLoaderIniciarSesion,
                                   FXMLLoader fxmlLoaderJefatura, FXMLLoader fxmlLoaderConsultasPadre,
                                   FXMLLoader fxmlLoaderPonerParte, CacheAuthorization ca) {
        this.fxmlLoaderIniciarSesion = fxmlLoaderIniciarSesion;
        this.fxmlLoaderJefatura = fxmlLoaderJefatura;
        this.fxmlLoaderConsultasPadre = fxmlLoaderConsultasPadre;
        this.fxmlLoaderPonerParte = fxmlLoaderPonerParte;
        this.serviciosUsuario = serviciosUsuario;
        this.ca = ca;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadIniciarSesion();
        preloadJefatura();
        preloadConsultasPadre();
        preloadPonerParte();
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

    private void preloadJefatura() {
        if (pantallaJefatura == null) {
            try {
                pantallaJefatura = fxmlLoaderJefatura.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_JEFATURA_FXML));
                jefaturaController = fxmlLoaderJefatura.getController();
                jefaturaController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void preloadPonerParte() {
        if (pantallaPonerParte == null) {
            try {
                pantallaPonerParte = fxmlLoaderPonerParte.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_PONERPARTE_FXML));
                ponerParteController = fxmlLoaderPonerParte.getController();
                ponerParteController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void preloadConsultasPadre() {
        if (pantallaConsultasPadre == null) {
            try {
                pantallaConsultasPadre = fxmlLoaderConsultasPadre.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_CONSULTASPADRE_FXML));
                consultasPadreController = fxmlLoaderConsultasPadre.getController();
                consultasPadreController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    //--------------------------------------------------------------------------------------------------------//

    public void mostrarPantallaIniciarSesion() {
        if (pantallaIniciarSesion != null) {
            if (pantallaPrincipal.getCenter() != null) {
                pantallaPrincipal.setCenter(null);
            }
            pantallaPrincipal.setCenter(pantallaIniciarSesion);
        }
    }

    public void mostrarPantallaJefatura() {
        if (pantallaJefatura != null) {
            if (pantallaPrincipal.getCenter() != null) {
                pantallaPrincipal.setCenter(null);
            }
            pantallaPrincipal.setCenter(pantallaJefatura);
        }
    }

    public void mostrarPantallaPonerParte() {
        if (pantallaPonerParte != null) {
            if (pantallaPrincipal.getCenter() != null) {
                pantallaPrincipal.setCenter(null);
            }
            pantallaPrincipal.setCenter(pantallaPonerParte);
        }
    }

    public void mostrarPantallaConsultasPadre() {
        if (pantallaConsultasPadre != null) {
            if (pantallaPrincipal.getCenter() != null) {
                pantallaPrincipal.setCenter(null);
            }
            pantallaPrincipal.setCenter(pantallaConsultasPadre);
        }
    }

    //--------------------------------------------------------------------------------------------------------//

    @FXML
    private void jefatura() {
        mostrarPantallaJefatura();
        jefaturaController.actualizarDatos();
    }

    @FXML
    private void ponerParte() {
        mostrarPantallaPonerParte();
        ponerParteController.actualizarDatos();
    }

    @FXML
    private void consultasPadre() {
        mostrarPantallaConsultasPadre();
        consultasPadreController.actualizarDatos();
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
        serviciosUsuario.hacerLogout();
        mostrarPantallaIniciarSesion();
        if (menuJefatura.isVisible()) {
            menuJefatura.setVisible(false);
        }
        if (menuProfe.isVisible()) {
            menuProfe.setVisible(false);
        }
        if (menuPadre.isVisible()) {
            menuPadre.setVisible(false);
        }
        if (ca.getNombre() != null) {
            ca.setNombre(null);
            ca.setContraseña(null);
            ca.setToken(null);
        }
    }

    //----------------------------------

    public void irAPrincipalAdmin() {
        pantallaPrincipal.setCenter(pantallaJefatura);
        jefaturaController.actualizarDatos();
        menuJefatura.setVisible(true);
    }

    public void irAPrincipalUsuario() {
        pantallaPrincipal.setCenter(pantallaConsultasPadre);
        consultasPadreController.actualizarDatos();
        pantallaPrincipal.setVisible(true);
        menuPadre.setVisible(true);
    }

    public void irAPrincipalProfe() {
        mostrarPantallaPonerParte();
        ponerParteController.actualizarDatos();
        pantallaPrincipal.setVisible(true);
        menuProfe.setVisible(true);
    }
}