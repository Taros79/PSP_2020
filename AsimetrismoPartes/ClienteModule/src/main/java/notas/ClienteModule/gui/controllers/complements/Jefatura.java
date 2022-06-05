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
    private ListView<ParteDesencriptadoDTO> listViewPartes;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosParte serviciosParte;

    @Inject
    public Jefatura(ServiciosParte serviciosParte, ServiciosUsuario serviciosUsuario) {
        this.serviciosParte = serviciosParte;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    public void limpiarDatosJefatura() {
        listViewPartes.getItems().clear();
    }

    public void actualizarDatos() {
        serviciosParte.getPartesByUser(pantallaPrincipal.getUsuarioLoginPrincipal().getId())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewPartes.getItems().clear();
                                                    for (ParteDesencriptadoDTO parte : action) {
                                                        //Para que quede bonito el estado y no sea la id
                                                        parte.setTipoEstado(parte.getIdTipoEstado());
                                                        listViewPartes.getItems().add(parte);
                                                    }
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
}