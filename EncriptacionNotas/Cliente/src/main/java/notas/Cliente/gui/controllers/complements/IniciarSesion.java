package notas.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import notas.Cliente.Servicios.ServiciosUsuario;
import notas.Cliente.gui.controllers.FXMLPrincipalController;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import notas.Cliente.dao.utils.CacheAuthorization;
import notas.Cliente.gui.ConstantesGUI;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class IniciarSesion implements Initializable {

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPass;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private CacheAuthorization cacheAuthorization;
    private ServiciosUsuario serviciosUsuario;

    @Inject
    public IniciarSesion(CacheAuthorization cacheAuthorization, ServiciosUsuario serviciosUsuario) {
        this.cacheAuthorization = cacheAuthorization;
        this.serviciosUsuario = serviciosUsuario;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    @FXML
    private void hacerLogin() {
        cacheAuthorization.setNombre(textFieldNombre.getText());
        cacheAuthorization.setContraseña(textFieldPass.getText());
        serviciosUsuario.hacerLogin(textFieldNombre.getText(), textFieldPass.getText())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    a.setContentText(action.getNombre() + " a iniciado sesion");
                                                    a.showAndWait();
                                                    pantallaPrincipal.setUsuarioLoginPrincipal(action);
                                                    if (action.getIdTipoUsuario() == 2) {
                                                        pantallaPrincipal.irAPrincipalAdmin();
                                                    } else if (action.getIdTipoUsuario() == 3) {
                                                        pantallaPrincipal.irAPrincipalUsuario();
                                                    } else {
                                                        pantallaPrincipal.irAPrincipalProfe();
                                                    }
                                                }
                                        )
                                        .peekLeft(error -> {
                                            a.setContentText(error);
                                            a.showAndWait();
                                        }),
                        throwable -> {
                            a.setContentText(ConstantesGUI.FALLO_AL_REALIZAR_LA_PETICION);
                            a.showAndWait();
                        }
                );
        pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }
}