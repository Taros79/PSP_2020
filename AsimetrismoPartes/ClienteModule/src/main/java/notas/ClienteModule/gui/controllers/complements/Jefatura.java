package notas.ClienteModule.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import notas.ClienteModule.Servicios.ServiciosParte;
import notas.ClienteModule.gui.ConstantesGUI;
import notas.ClienteModule.gui.controllers.FXMLPrincipalController;
import notas.CommonModule.modelo.Parte;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;


public class Jefatura implements Initializable {

    @FXML
    private ListView<Parte> listViewPartes;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosParte serviciosParte;

    @Inject
    public Jefatura(ServiciosParte serviciosParte) {
        this.serviciosParte = serviciosParte;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    public void actualizarDatos() {
        serviciosParte.getAllPartes()
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
}