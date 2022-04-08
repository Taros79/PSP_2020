package rol.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import rol.Cliente.Servicios.ServiciosDote;
import rol.Cliente.Servicios.ServiciosPersonaje;
import rol.Cliente.gui.ConstantesGUI;
import rol.Cliente.gui.controllers.FXMLPrincipalController;
import rol.Common.modelo.Dote;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.RelacionId;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class DotesPj implements Initializable {

    @FXML
    private ListView<Dote> listViewAllDotes;
    @FXML
    private ListView<Personaje> listViewPersonajes;
    @FXML
    private ListView<Dote> listViewDotes;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosPersonaje serviciosPersonaje;
    private ServiciosDote serviciosDote;

    @Inject
    public DotesPj(ServiciosPersonaje serviciosPersonaje, ServiciosDote serviciosDote) {
        this.serviciosPersonaje = serviciosPersonaje;
        this.serviciosDote = serviciosDote;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    private void getDotes() {
        serviciosDote.getAllDotes()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewAllDotes.getItems().clear();
                                                    listViewAllDotes.getItems().addAll(action);
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

    private void getDotesPersonaje() {
        serviciosDote.getDotesByIdPersonaje(listViewPersonajes.getSelectionModel().getSelectedItem().getId())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewDotes.getItems().clear();
                                                    listViewDotes.getItems().addAll(action);
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

    public void actualizarDatos() {
        listViewDotes.getItems().clear();
        listViewPersonajes.getItems().clear();
        serviciosPersonaje.getPersonajesByIdUsuario(pantallaPrincipal.getUsuarioLoginPrincipal().getId())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewPersonajes.getItems().addAll(action);
                                                    getDotes();
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
    private void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewPersonajes.getSelectionModel().getSelectedItem() != null) {
            getDotesPersonaje();
        }
    }


    @FXML
    private void addDote() {
        if (!listViewPersonajes.getSelectionModel().isEmpty() && !listViewAllDotes.getSelectionModel().isEmpty()) {
            serviciosDote.addDoteToPersonaje(new RelacionId(listViewAllDotes.getSelectionModel().getSelectedItem().getId(),
                            listViewPersonajes.getSelectionModel().getSelectedItem().getId()))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getDotesPersonaje();
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
            a.setContentText(ConstantesGUI.SELECCIONA_PERSONAJE_Y_DOTE_DE_LA_LISTA_GENERAL_DE_DOTES);
            a.showAndWait();
        }
    }

    @FXML
    private void delDote() {
        if (!listViewDotes.getSelectionModel().isEmpty() && !listViewPersonajes.getSelectionModel().isEmpty()) {
            serviciosDote.delDoteToPersonaje(listViewDotes.getSelectionModel().getSelectedItem().getId(),
                            listViewPersonajes.getSelectionModel().getSelectedItem().getId())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getDotesPersonaje();
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
            a.setContentText(ConstantesGUI.SELECCIONA_PERSONAJE_Y_DOTE_DE_TU_LISTA_A_ELIMINAR);
            a.showAndWait();
        }
    }


}