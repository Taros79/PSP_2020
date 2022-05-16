package notas.Cliente.gui.controllers.complements;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import notas.Cliente.Servicios.ServiciosParte;
import notas.Cliente.gui.ConstantesGUI;
import notas.Cliente.gui.controllers.FXMLPrincipalController;
import notas.Common.modelo.Parte;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

import static notas.Cliente.gui.utils.OnlyNumbers.onlyNum;


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

    @FXML
    private void aceptar() {
        serviciosParte.updateParte(
                        listViewPartes.getSelectionModel().getSelectedItem().getId(),
                        2)
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
    }

    @FXML
    private void rechazar() {
        serviciosParte.updateParte(
                        listViewPartes.getSelectionModel().getSelectedItem().getId(),
                        3)
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
    }
}