package notas.ClienteModule.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import notas.ClienteModule.Servicios.ServiciosParte;
import notas.ClienteModule.gui.ConstantesGUI;
import notas.ClienteModule.gui.controllers.FXMLPrincipalController;
import notas.CommonModule.modeloDTO.ParteDesencriptadoDTO;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsultasPadre implements Initializable {

    @FXML
    private ListView<ParteDesencriptadoDTO> listViewPartes;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosParte serviciosParte;

    @Inject
    public ConsultasPadre(ServiciosParte serviciosParte) {
        this.serviciosParte = serviciosParte;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }


    public void limpiarDatosPadre() {
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

    @FXML
    private void firmarParte() {
        if (listViewPartes.getSelectionModel().getSelectedItem() != null) {
            serviciosParte.firmarPartePadre(pantallaPrincipal.getUsuarioLoginPrincipal().getId(),
                            listViewPartes.getSelectionModel().getSelectedItem().getId(),
                            listViewPartes.getSelectionModel().getSelectedItem().getDescripcion())
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
            a = new Alert(Alert.AlertType.ERROR, ConstantesGUI.CAMPOS_VACIOS);
            a.showAndWait();
        }
    }
}