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


public class Jefatura implements Initializable {

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPass;
    @FXML
    private ListView<ParteDesencriptadoDTO> listViewPartes;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosParte serviciosParte;
    private ServiciosUsuario serviciosUsuario;

    @Inject
    public Jefatura(ServiciosParte serviciosParte, ServiciosUsuario serviciosUsuario) {
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

    public void actualizarDatos() {
        serviciosParte.getPartesByUser(pantallaPrincipal.getUsuarioLoginPrincipal().getId())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewPartes.getItems().clear();
                                                    listViewPartes.getItems().addAll(action);
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

    public void updateParte(int estado) {
        if (listViewPartes.getSelectionModel().getSelectedItem() != null) {
            serviciosParte.updateParte(
                            listViewPartes.getSelectionModel().getSelectedItem().getId(),
                            estado)
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        actualizarDatos();
                                                        a = new Alert(Alert.AlertType.INFORMATION, action);
                                                        a.showAndWait();
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
            a = new Alert(Alert.AlertType.ERROR, ConstantesGUI.SELECCIONA_EN_LA_LISTA);
            a.showAndWait();
        }
    }

    @FXML
    private void aceptar() {
        updateParte(2);
    }

    @FXML
    private void rechazar() {
        updateParte(3);
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