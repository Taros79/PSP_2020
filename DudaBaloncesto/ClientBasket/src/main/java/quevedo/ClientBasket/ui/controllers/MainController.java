package quevedo.ClientBasket.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import quevedo.ClientBasket.dao.modelo.UserCached;
import quevedo.ClientBasket.services.IdentificacionService;
import quevedo.ClientBasket.ui.controllers.entrar.BienvenidaController;
import quevedo.ClientBasket.ui.controllers.entrar.LoginController;
import quevedo.ClientBasket.ui.controllers.entrar.RegisterAdminController;
import quevedo.ClientBasket.ui.controllers.entrar.RegisterController;
import quevedo.ClientBasket.ui.controllers.gestionar.AddController;
import quevedo.ClientBasket.ui.controllers.visualizar.VisualizarController;
import quevedo.ClientBasket.utils.ConstantesJavaFx;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class MainController implements Initializable {


    private final FXMLLoader loaderLogin;

    private final FXMLLoader loaderRegister;

    private final FXMLLoader loaderVer;

    private final FXMLLoader loaderAdd;

    private final FXMLLoader loaderWelcome;

    private final FXMLLoader loaderAddAdmin;
    private final IdentificacionService identificacionService;
    @FXML
    private Menu menuGestion;
    @FXML
    private Menu menuVer;
    private AnchorPane apLogin;
    private LoginController loginController;
    private AnchorPane apRegister;
    private RegisterController registerController;
    private AnchorPane apVer;
    private VisualizarController verConttroller;
    private AnchorPane apAdd;
    private AddController addController;
    private AnchorPane apWelcome;
    private BienvenidaController bienvenidaController;
    private AnchorPane apAddAdmin;
    private RegisterAdminController addAdminController;
    private Alert alert;
    @FXML
    private BorderPane root;

    private final UserCached userCached;

    @Inject
    public MainController(IdentificacionService identificacionService, FXMLLoader loaderLogin,
                          FXMLLoader loaderRegister, FXMLLoader loaderAdd, FXMLLoader loaderVer,
                          FXMLLoader loaderWelcome, FXMLLoader loaderAddAdmin, UserCached userCached) {
        this.identificacionService = identificacionService;
        this.loaderLogin = loaderLogin;
        this.loaderRegister = loaderRegister;
        this.loaderAdd = loaderAdd;
        this.loaderVer = loaderVer;
        this.loaderWelcome = loaderWelcome;
        this.loaderAddAdmin = loaderAddAdmin;
        this.userCached = userCached;
    }

    public BorderPane getRoot() {
        return root;
    }

    private void preLoadVer() {
        try {
            if (apVer == null) {
                apVer = loaderVer.load(getClass().getResourceAsStream(ConstantesJavaFx.FXML_VER_FXML));
                verConttroller = loaderVer.getController();
                verConttroller.setRoot(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void preLoadAddAdmin() {
        try {
            if (apAddAdmin == null) {
                apAddAdmin = loaderAddAdmin.load(getClass().getResourceAsStream(ConstantesJavaFx.FXML_ADD_ADMIN));
                addAdminController = loaderAddAdmin.getController();
                addAdminController.setRoot(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void preLoadAdd() {
        try {
            if (apAdd == null) {
                apAdd = loaderAdd.load(getClass().getResourceAsStream(ConstantesJavaFx.FXML_ADD_FXML));
                addController = loaderAdd.getController();
                addController.setRoot(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    private void preLoadLogin() {
        try {
            if (apLogin == null) {
                apLogin = loaderLogin.load(getClass().getResourceAsStream(ConstantesJavaFx.FXML_ENTRAR_LOGIN_SCREEN_FXML));
                loginController = loaderLogin.getController();
                loginController.setRoot(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void preLoadRegister() {
        try {
            if (apRegister == null) {
                apRegister = loaderRegister.load(getClass().getResourceAsStream(ConstantesJavaFx.FXML_ENTRAR_REGISTER_SCREEN_FXML));
                registerController = loaderRegister.getController();
                registerController.setRoot(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void preLoadWelcome() {
        try {
            if (apWelcome == null) {
                apWelcome = loaderWelcome.load(getClass().getResourceAsStream(ConstantesJavaFx.FXML_BIENVENIDA_SCREEN_FXML));
                bienvenidaController = loaderWelcome.getController();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @FXML
    private void loadRegister() {
        if (userCached.getUser() != null) {
            userCached.setUser(null);
            userCached.setPass(null);
            userCached.setJwt(null);
        }
        root.setCenter(apRegister);
    }

    @FXML
    private void loadLogin() {
        if (userCached.getUser() != null) {
            userCached.setUser(null);
            userCached.setPass(null);
            userCached.setJwt(null);
        }
        root.setCenter(apLogin);
    }

    @FXML
    private void loadVer() {
        verConttroller.loadPartidos();
        root.setCenter(apVer);

    }

    @FXML
    private void loadAddAdmin() {
        root.setCenter(apAddAdmin);
    }

    @FXML
    private void loadAdd() {
        addController.load();
        root.setCenter(apAdd);
    }


    public void activarOpcionesUser(String nombre) {
        menuVer.setVisible(true);
        bienvenidaController.getLabelNombre().setText(nombre);
        root.setCenter(apWelcome);

    }

    public void activarOpcionesAdmin(String nombre) {
        menuGestion.setVisible(true);
        menuVer.setVisible(true);
        bienvenidaController.getLabelNombre().setText(nombre);
        root.setCenter(apWelcome);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preLoadLogin();
        preLoadRegister();
        preLoadAdd();
        preLoadVer();
        preLoadWelcome();
        preLoadAddAdmin();
        menuGestion.setVisible(false);
        menuVer.setVisible(false);
        root.setCenter(apLogin);


    }


}
