package notas.ClienteModule.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import notas.ClienteModule.Servicios.ServiciosParte;
import notas.ClienteModule.Servicios.ServiciosUsuario;
import notas.ClienteModule.gui.ConstantesGUI;
import notas.ClienteModule.gui.controllers.FXMLPrincipalController;
import notas.CommonModule.modelo.Usuario;
import notas.CommonModule.modeloDTO.ParteDesencriptadoDTO;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class Administracion implements Initializable {

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPass;
    @FXML
    private ListView<Usuario> listViewUsuarios;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosParte serviciosParte;
    private ServiciosUsuario serviciosUsuario;

    @Inject
    public Administracion(ServiciosParte serviciosParte, ServiciosUsuario serviciosUsuario) {
        this.serviciosParte = serviciosParte;
        this.serviciosUsuario = serviciosUsuario;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    public void limpiarDatosAdministracion() {
        listViewUsuarios.getItems().clear();
        textFieldNombre.clear();
        textFieldPass.clear();
    }

    public void actualizarDatos() {
        serviciosUsuario.getAllUsuarios()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewUsuarios.getItems().clear();
                                                    listViewUsuarios.getItems().addAll(action);
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
    }

    @FXML
    private void registrar() {
        if (!textFieldNombre.getText().isEmpty() || !textFieldPass.getText().isEmpty() || comboBox.getSelectionModel().getSelectedItem() != null) {
            int id;
            if (Objects.equals(comboBox.getSelectionModel().getSelectedItem(), "Profesor"))
                id = 1;
            else {
                id = 3;
            }
            serviciosUsuario.addUsuario(new Usuario(textFieldNombre.getText(), textFieldPass.getText(), id))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        actualizarDatos();
                                                        a.setContentText(action);
                                                        a.showAndWait();
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
        } else {
            a.setContentText(ConstantesGUI.CAMPOS_VACIOS);
            a.showAndWait();
        }
    }
}