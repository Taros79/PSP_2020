package rol.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import rol.Cliente.Servicios.ServiciosUsuario;
import rol.Cliente.gui.ConstantesGUI;
import rol.Cliente.gui.controllers.FXMLPrincipalController;
import rol.Common.modelo.Usuario;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;


public class Registrarse implements Initializable {

    @FXML
    private ComboBox<String> comboTipoUsuario;
    @FXML
    private TextField tfPass;
    @FXML
    private TextField tfCorreo;


    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosUsuario serviciosUsuario;

    @Inject
    public Registrarse(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    public void actualizarDatos() {
        tfCorreo.clear();
        tfPass.clear();
        comboTipoUsuario.getSelectionModel().clearSelection();
    }

    private void hacerLogin() {
        serviciosUsuario.hacerLoging(tfCorreo.getText(), tfPass.getText())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    if (action.getTipo_Usuario() == 3) {
                                                        pantallaPrincipal.irAPrincipalAdmin();
                                                    } else {
                                                        pantallaPrincipal.irAPrincipalUsuario();
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

    @FXML
    private void addUsuario() {
        if (!tfCorreo.getText().isEmpty() || !tfPass.getText().isEmpty() || !comboTipoUsuario.getSelectionModel().isEmpty()) {
            serviciosUsuario.addUsuario(new Usuario(
                            tfCorreo.getText(),
                            tfPass.getText(),
                            comboTipoUsuario.getSelectionModel().getSelectedIndex() + 1,
                            0))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        a = new Alert(Alert.AlertType.INFORMATION, action + " creado con exito");
                                                        a.showAndWait();
                                                        hacerLogin();
                                                    }
                                            )
                                            .peekLeft(error -> {
                                                a = new Alert(Alert.AlertType.ERROR, error);
                                                a.showAndWait();
                                            }),
                            throwable -> {
                                a = new Alert(Alert.AlertType.ERROR, ConstantesGUI.FALLO_AL_REALIZAR_LA_PETICION);
                                a.showAndWait();
                            }
                    );
            pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.CAMPOS_VACIOS);
            a.showAndWait();
        }
    }
}