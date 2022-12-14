package rol.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import rol.Cliente.Servicios.ServiciosHechizo;
import rol.Cliente.Servicios.ServiciosPersonaje;
import rol.Cliente.gui.ConstantesGUI;
import rol.Cliente.gui.controllers.FXMLPrincipalController;
import rol.Common.modelo.Hechizo;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.RelacionId;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class HechizosPj implements Initializable {

    @FXML
    private ListView<Hechizo> listViewAllHechizos;
    @FXML
    private ListView<Personaje> listViewPersonajes;
    @FXML
    private ListView<Hechizo> listViewHechizos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosPersonaje serviciosPersonaje;
    private ServiciosHechizo serviciosHechizo;

    @Inject
    public HechizosPj(ServiciosPersonaje serviciosPersonaje, ServiciosHechizo serviciosHechizo) {
        this.serviciosPersonaje = serviciosPersonaje;
        this.serviciosHechizo = serviciosHechizo;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    private void getHechizos() {
        serviciosHechizo.getAllHechizos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewAllHechizos.getItems().clear();
                                                    listViewAllHechizos.getItems().addAll(action);
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

    private void getHechizosPersonaje() {
        serviciosHechizo.getHechizosByIdPersonaje(listViewPersonajes.getSelectionModel().getSelectedItem().getId())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewHechizos.getItems().clear();
                                                    listViewHechizos.getItems().addAll(action);
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
        listViewHechizos.getItems().clear();
        listViewPersonajes.getItems().clear();
        getHechizos();
        serviciosPersonaje.getPersonajesByIdUsuario(pantallaPrincipal.getUsuarioLoginPrincipal().getId())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {

                                                    listViewPersonajes.getItems().addAll(action);
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
            getHechizosPersonaje();
        }
    }


    @FXML
    private void addHechizo() {
        if (!listViewPersonajes.getSelectionModel().isEmpty() && !listViewAllHechizos.getSelectionModel().isEmpty()) {
            serviciosHechizo.addHechizoToPersonaje(new RelacionId(listViewAllHechizos.getSelectionModel().getSelectedItem().getId(),
                            listViewPersonajes.getSelectionModel().getSelectedItem().getId()))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getHechizosPersonaje();
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
            a.setContentText(ConstantesGUI.SELECCIONA_PERSONAJE_Y_HECHIZO_DE_LA_LISTA_GENERAL_DE_HECHIZOS);
            a.showAndWait();
        }
    }

    @FXML
    private void delHechizo() {
        if (!listViewHechizos.getSelectionModel().isEmpty() && !listViewPersonajes.getSelectionModel().isEmpty()) {
            serviciosHechizo.delHechizoToPersonaje(listViewHechizos.getSelectionModel().getSelectedItem().getId(),
                            listViewPersonajes.getSelectionModel().getSelectedItem().getId())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getHechizosPersonaje();
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
            a.setContentText(ConstantesGUI.SELECCIONA_PERSONAJE_Y_HECHIZO_DE_TU_LISTA_A_ELIMINAR);
            a.showAndWait();
        }
    }


}