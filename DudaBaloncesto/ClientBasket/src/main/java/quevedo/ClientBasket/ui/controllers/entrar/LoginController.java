package quevedo.ClientBasket.ui.controllers.entrar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import quevedo.ClientBasket.dao.modelo.UserCached;
import quevedo.ClientBasket.services.IdentificacionService;
import quevedo.ClientBasket.ui.controllers.MainController;
import quevedo.ClientBasket.utils.ConstantesMensajes;
import quevedo.common.modelo.User;

import javax.inject.Inject;


public class LoginController {
    private final IdentificacionService identificacionService;
    @FXML
    private TextField taCorreo;
    @FXML
    private TextField taPasswd;
    private MainController root;
    private Alert alert;
    private final UserCached userCached;

    @Inject
    public LoginController(IdentificacionService identificacionService, UserCached userCached) {
        this.identificacionService = identificacionService;
        this.userCached = userCached;
    }

    public void setRoot(MainController root) {
        this.root = root;
    }

    @FXML
    private void recuperarPassw(ActionEvent actionEvent) {


        if (taCorreo.getText().isBlank()) {
            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.EL_CAMPO_DE_CORREO_NO_PUEDE_ESTAR_VACIO);
            alert.showAndWait();
        } else {
            identificacionService.cambiarPassw(taCorreo.getText())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        alert = new Alert(Alert.AlertType.INFORMATION, ConstantesMensajes.CONFIRME_EL_CAMBIO_DE_CONTRASEÑA_A_TRAVÉS_DE_SU_CORREO);
                                                        alert.showAndWait();
                                                        taCorreo.clear();
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
        }


    }


    @FXML
    private void acceder() {

        if (taCorreo.getText().isBlank() || taPasswd.getText().isBlank()) {
            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.INTRODUCE_USUARIO_Y_CONTARSEÑA);
            alert.show();
        } else {
            userCached.setUser(taCorreo.getText());
            userCached.setPass(taPasswd.getText());

            identificacionService.login(new User(taCorreo.getText(), taPasswd.getText()))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        if (action.getTipoUser() == 1) {
                                                            root.activarOpcionesAdmin(taCorreo.getText());
                                                        } else {
                                                            root.activarOpcionesUser(taCorreo.getText());
                                                        }
                                                        taCorreo.clear();
                                                        taPasswd.clear();

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


        }


    }


}
