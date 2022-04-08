package rol.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import rol.Cliente.Servicios.ServiciosObjeto;
import rol.Cliente.Servicios.ServiciosPersonaje;
import rol.Cliente.gui.ConstantesGUI;
import rol.Cliente.gui.controllers.FXMLPrincipalController;
import rol.Common.modelo.Objeto;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.RelacionId;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class ObjetosPj implements Initializable {

    @FXML
    private ListView<Objeto> listViewAllObjetos;
    @FXML
    private ListView<Personaje> listViewPersonajes;
    @FXML
    private ListView<Objeto> listViewObjetos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosPersonaje serviciosPersonaje;
    private ServiciosObjeto serviciosObjeto;

    @Inject
    public ObjetosPj(ServiciosPersonaje serviciosPersonaje, ServiciosObjeto serviciosObjeto) {
        this.serviciosPersonaje = serviciosPersonaje;
        this.serviciosObjeto = serviciosObjeto;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    private void getObjetos() {
        serviciosObjeto.getAllObjetos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewAllObjetos.getItems().clear();
                                                    listViewAllObjetos.getItems().addAll(action);
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

    private void getObjetosPersonaje() {
        serviciosObjeto.getObjetosByIdPersonaje(listViewPersonajes.getSelectionModel().getSelectedItem().getId())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewObjetos.getItems().clear();
                                                    listViewObjetos.getItems().addAll(action);
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
        listViewObjetos.getItems().clear();
        listViewPersonajes.getItems().clear();
        serviciosPersonaje.getPersonajesByIdUsuario(pantallaPrincipal.getUsuarioLoginPrincipal().getId())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    listViewPersonajes.getItems().addAll(action);
                                                    getObjetos();
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
            getObjetosPersonaje();
        }
    }


    @FXML
    private void addObjeto() {
        if (!listViewPersonajes.getSelectionModel().isEmpty() && !listViewAllObjetos.getSelectionModel().isEmpty()) {
            serviciosObjeto.addObjetoToPersonaje(new RelacionId(listViewAllObjetos.getSelectionModel().getSelectedItem().getId(),
                            listViewPersonajes.getSelectionModel().getSelectedItem().getId()))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getObjetosPersonaje();
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
            a.setContentText(ConstantesGUI.SELECCIONA_PERSONAJE_Y_OBJETO_DE_LA_LISTA_GENERAL_DE_OBJETOS);
            a.showAndWait();
        }
    }

    @FXML
    private void delObjeto() {
        if (!listViewObjetos.getSelectionModel().isEmpty() && !listViewPersonajes.getSelectionModel().isEmpty()) {
            serviciosObjeto.delObjetoToPersonaje(listViewObjetos.getSelectionModel().getSelectedItem().getId(),
                            listViewPersonajes.getSelectionModel().getSelectedItem().getId())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getObjetosPersonaje();
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
            a.setContentText(ConstantesGUI.SELECCIONA_PERSONAJE_Y_OBJETO_DE_TU_LISTA_A_ELIMINAR);
            a.showAndWait();
        }
    }


}