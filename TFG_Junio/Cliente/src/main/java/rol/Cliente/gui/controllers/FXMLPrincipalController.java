package rol.Cliente.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import rol.Cliente.Servicios.ServiciosUsuario;
import rol.Cliente.gui.ConstantesGUI;
import rol.Cliente.gui.controllers.complements.*;
import rol.Common.modelo.Usuario;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class FXMLPrincipalController implements Initializable {

    private final FXMLLoader fxmlLoaderIniciarSesion;
    private final FXMLLoader fxmlLoaderPerfil;
    private final FXMLLoader fxmlLoaderHechizosPj;
    private final FXMLLoader fxmlLoaderObjetosPj;
    private final FXMLLoader fxmlLoaderDotesPj;
    private final FXMLLoader fxmlLoaderPersonajes;
    private final FXMLLoader fxmlLoaderCrudHOD;
    private final FXMLLoader fxmlLoaderRegistrarse;

    @FXML
    private Menu menuAdmin;
    @FXML
    private Menu menuUsuario;

    @FXML
    private BorderPane pantallaPrincipal;

    private AnchorPane pantallaIniciarSesion;
    private IniciarSesion iniciarSesionController;
    private AnchorPane pantallaPerfil;
    private Perfil perfilController;
    private AnchorPane pantallaHechizosPj;
    private HechizosPj hechizosController;
    private AnchorPane pantallaObjetosPj;
    private ObjetosPj objetosPjController;
    private AnchorPane pantallaDotesPj;
    private DotesPj dotesPjController;
    private AnchorPane pantallaPersonajes;
    private Personajes personajesController;
    private AnchorPane pantallaCrudHOD;
    private CrudHOD crudHODController;
    private AnchorPane pantallaRegistrarse;
    private Registrarse registrarseController;

    private ServiciosUsuario serviciosUsuario;
    private Usuario usuarioLogin;


    @Inject
    public FXMLPrincipalController(ServiciosUsuario serviciosUsuario, FXMLLoader fxmlLoaderIniciarSesion, FXMLLoader fxmlLoaderPerfil, FXMLLoader fxmlLoaderHechizosPj,
                                   FXMLLoader fxmlLoaderObjetosPj, FXMLLoader fxmlLoaderDotesPj, FXMLLoader fxmlLoaderPersonajes,
                                   FXMLLoader fxmlLoaderCrudHOD, FXMLLoader fxmlLoaderRegistrarse) {
        this.fxmlLoaderIniciarSesion = fxmlLoaderIniciarSesion;
        this.fxmlLoaderPerfil = fxmlLoaderPerfil;
        this.fxmlLoaderHechizosPj = fxmlLoaderHechizosPj;
        this.fxmlLoaderObjetosPj = fxmlLoaderObjetosPj;
        this.fxmlLoaderDotesPj = fxmlLoaderDotesPj;
        this.fxmlLoaderPersonajes = fxmlLoaderPersonajes;
        this.fxmlLoaderCrudHOD = fxmlLoaderCrudHOD;
        this.fxmlLoaderRegistrarse = fxmlLoaderRegistrarse;
        this.serviciosUsuario = serviciosUsuario;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadIniciarSesion();
        preloadPerfil();
        preloadHechizosPj();
        preloadObjetosPj();
        preloadDotesPj();
        preloadPersonajes();
        preloadCrudHOD();
        preloadRegistrarse();
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

    private void preloadHechizosPj() {
        if (pantallaHechizosPj == null) {
            try {
                pantallaHechizosPj = fxmlLoaderHechizosPj.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_HECHIZOS_PJ_FXML));
                hechizosController = fxmlLoaderHechizosPj.getController();
                hechizosController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void preloadObjetosPj() {
        if (pantallaObjetosPj == null) {
            try {
                pantallaObjetosPj = fxmlLoaderObjetosPj.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_OBJETOS_PJ_FXML));
                objetosPjController = fxmlLoaderObjetosPj.getController();
                objetosPjController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void preloadDotesPj() {
        if (pantallaDotesPj == null) {
            try {
                pantallaDotesPj = fxmlLoaderDotesPj.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_DOTES_PJ_FXML));
                dotesPjController = fxmlLoaderDotesPj.getController();
                dotesPjController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void preloadPersonajes() {
        if (pantallaPersonajes == null) {
            try {
                pantallaPersonajes = fxmlLoaderPersonajes.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_CREAR_PERSONAJES_FXML));
                personajesController = fxmlLoaderPersonajes.getController();
                personajesController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void preloadCrudHOD() {
        if (pantallaCrudHOD == null) {
            try {
                pantallaCrudHOD = fxmlLoaderCrudHOD.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_CRUD_HOD_FXML));
                crudHODController = fxmlLoaderCrudHOD.getController();
                crudHODController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void preloadRegistrarse() {
        if (pantallaRegistrarse == null) {
            try {
                pantallaRegistrarse = fxmlLoaderRegistrarse.load(getClass()
                        .getResourceAsStream(ConstantesGUI.FXML_COMPLEMENTS_REGISTRARSE_FXML));
                registrarseController = fxmlLoaderRegistrarse.getController();
                registrarseController.setPerfil(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @FXML
    private void iniciarSesion() {
        pantallaPrincipal.setCenter(pantallaIniciarSesion);
    }

    @FXML
    private void perfil() {
        pantallaPrincipal.setCenter(pantallaPerfil);
        perfilController.actualizarDatos();
    }

    @FXML
    private void hechizosPj() {
        pantallaPrincipal.setCenter(pantallaHechizosPj);
        hechizosController.actualizarDatos();
    }

    @FXML
    private void dotesPj() {
        pantallaPrincipal.setCenter(pantallaDotesPj);
        dotesPjController.actualizarDatos();
    }

    @FXML
    private void objetosPj() {
        pantallaPrincipal.setCenter(pantallaObjetosPj);
        objetosPjController.actualizarDatos();
    }

    @FXML
    private void personajes() {
        pantallaPrincipal.setCenter(pantallaPersonajes);
        personajesController.actualizarDatos();
    }

    @FXML
    private void crudHOD() {
        pantallaPrincipal.setCenter(pantallaCrudHOD);
        crudHODController.actualizarDatos();
    }

    @FXML
    private void registrarse() {
        pantallaPrincipal.setCenter(pantallaRegistrarse);
        registrarseController.actualizarDatos();
    }

    @FXML
    private void logout() {
        serviciosUsuario.hacerLogout();
        pantallaPrincipal.setCenter(pantallaIniciarSesion);
        if (menuUsuario.isVisible()) {
            menuUsuario.setVisible(false);
        }
        if (menuAdmin.isVisible()) {
            menuAdmin.setVisible(false);
        }
    }
    //----------------------------------

    public void irAPrincipalAdmin() {
        pantallaPrincipal.setCenter(pantallaPerfil);
        menuUsuario.setVisible(true);
        menuAdmin.setVisible(true);
    }

    public void irAPrincipalUsuario() {
        pantallaPrincipal.setCenter(pantallaPerfil);
        pantallaPrincipal.setVisible(true);
        menuUsuario.setVisible(true);
    }


}