package notas.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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

    @Inject
    public IniciarSesion( CacheAuthorization cacheAuthorization) {
        this.cacheAuthorization = cacheAuthorization;
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
        /*serviciosUsuario.hacerLogin(textFieldNombre.getText(), textFieldPass.getText())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                            a.setContentText(action.getCorreo() + " a iniciado sesion");
                                            a.showAndWait();
                                            if (action.getTipo_Usuario() == 3) {
                                                pantallaPrincipal.irAPrincipalAdmin();
                                            } else {
                                                        pantallaPrincipal.irAPrincipalUsuario();
                                                    }
                                                    pantallaPrincipal.setUsuarioLoginPrincipal(action);
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
        pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);*/
    }
}