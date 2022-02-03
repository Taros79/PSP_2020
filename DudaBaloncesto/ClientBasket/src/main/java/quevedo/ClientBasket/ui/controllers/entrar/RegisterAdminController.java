package quevedo.ClientBasket.ui.controllers.entrar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import quevedo.ClientBasket.services.IdentificacionService;
import quevedo.ClientBasket.ui.controllers.MainController;
import quevedo.ClientBasket.utils.ConstantesMensajes;
import quevedo.common.modelo.User;

import javax.inject.Inject;

public class RegisterAdminController {

    private final IdentificacionService identificacionService;
    @FXML
    private TextField taCorreo;
    @FXML
    private TextField taPassw;
    @FXML
    private TextField taPasswd2;
    private Alert alert;
    private MainController root;

    @Inject
    public RegisterAdminController(IdentificacionService identificacionService) {
        this.identificacionService = identificacionService;
    }

    @FXML
    private void registrarse(ActionEvent actionEvent) {

        if (taPassw.getText().equals(taPasswd2.getText())) {

            identificacionService.registrarAdmin(new User(taCorreo.getText(), taPassw.getText()))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        alert = new Alert(Alert.AlertType.INFORMATION, action.getMensaje());
                                                        alert.showAndWait();
                                                        taCorreo.clear();
                                                        taPassw.clear();
                                                        taPasswd2.clear();
                                                    }
                                            )
                                            .peekLeft(error -> {
                                                alert = new Alert(Alert.AlertType.ERROR, error);
                                                alert.showAndWait();
                                            }),
                            throwable -> {
                                alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.FALLO_AL_REALIZAR_LA_PETICION);
                                alert.showAndWait();
                            }
                    );
            root.getRoot().setCursor(Cursor.WAIT);

        } else {
            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.LAS_CONTRASEÑAS_NO_COINCIDEN);
            alert.show();
        }


    }

    public void setRoot(MainController mainController) {
        this.root = mainController;
    }
}
