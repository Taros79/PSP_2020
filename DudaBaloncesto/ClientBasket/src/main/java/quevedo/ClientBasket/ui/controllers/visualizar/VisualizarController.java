package quevedo.ClientBasket.ui.controllers.visualizar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import quevedo.ClientBasket.services.VisualizarServices;
import quevedo.ClientBasket.ui.controllers.MainController;
import quevedo.ClientBasket.utils.ConstantesMensajes;
import quevedo.common.modelo.ResultadosPartidos;
import quevedo.common.modelo.dto.EquipoDTO;
import quevedo.common.modelo.dto.JornadaDTO;

import javax.inject.Inject;

public class VisualizarController {


    private final VisualizarServices visualizarServices;
    public ComboBox<EquipoDTO> cbEquipos;
    @FXML
    private ComboBox<JornadaDTO> cbJornadas;
    @FXML
    private ListView<ResultadosPartidos> lvTodosLosPartidos;

    @FXML
    private ListView<ResultadosPartidos> lvEquiposFiltrados;
    @FXML
    private ListView<ResultadosPartidos> lvEquiposJornadas;

    private MainController root;
    private Alert alert;

    @Inject
    public VisualizarController(VisualizarServices visualizarServices) {
        this.visualizarServices = visualizarServices;
    }

    public void setRoot(MainController root) {
        this.root = root;
    }

    @FXML
    private void filtroJornada(ActionEvent actionEvent) {


        visualizarServices.partidosPorJornadas(cbJornadas.getSelectionModel().getSelectedItem().getFecha())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    lvEquiposJornadas.getItems().clear();
                                                    lvEquiposJornadas.getItems().addAll(action);
                                                }
                                        )
                                        .peekLeft(error -> {
                                            alert = new Alert(Alert.AlertType.ERROR, error);
                                            alert.showAndWait();
                                        }),
                        throwable -> {
                            alert = new Alert(Alert.AlertType.ERROR, throwable.getMessage());
                            alert.showAndWait();
                        }
                );


        root.getRoot().setCursor(Cursor.WAIT);


    }

    @FXML
    private void filtroEquipo(ActionEvent actionEvent) {


        visualizarServices.partidosPorEquipo(cbEquipos.getSelectionModel().getSelectedItem().getNombre())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    lvEquiposFiltrados.getItems().clear();
                                                    if (action.isEmpty()) {
                                                        alert = new Alert(Alert.AlertType.INFORMATION, ConstantesMensajes.NO_HAY_RESULTADOS_CON_EL_NOMBRE_DE_ESE_EQUIPO);
                                                        alert.showAndWait();
                                                    } else {
                                                        lvEquiposFiltrados.getItems().addAll(action);
                                                    }

                                                }
                                        )
                                        .peekLeft(error -> {
                                            alert = new Alert(Alert.AlertType.ERROR, error);
                                            alert.showAndWait();
                                        }),
                        throwable -> {
                            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.FALLO_AL_REALIZAR_LA_PETICION);
                            alert.showAndWait();
                        }
                );


        root.getRoot().setCursor(Cursor.WAIT);


    }


    public void loadPartidos() {


        visualizarServices.getAllPartidos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    lvTodosLosPartidos.getItems().clear();
                                                    lvTodosLosPartidos.getItems().addAll(action);
                                                }
                                        )
                                        .peekLeft(error -> {
                                            alert = new Alert(Alert.AlertType.ERROR, error);
                                            alert.showAndWait();
                                        }),
                        throwable -> {
                            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.FALLO_AL_REALIZAR_LA_PETICION);
                            alert.showAndWait();
                        }
                );


        visualizarServices.allJornadas()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    cbJornadas.getItems().clear();
                                                    cbJornadas.getItems().addAll(action);
                                                }
                                        )
                                        .peekLeft(error -> {
                                            alert = new Alert(Alert.AlertType.ERROR, error);
                                            alert.showAndWait();
                                        }),
                        throwable -> {
                            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.FALLO_AL_REALIZAR_LA_PETICION);
                            alert.showAndWait();
                        }
                );

        visualizarServices.allEquipos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    cbEquipos.getItems().clear();
                                                    cbEquipos.getItems().addAll(action);
                                                }
                                        )
                                        .peekLeft(error -> {
                                            alert = new Alert(Alert.AlertType.ERROR, error);
                                            alert.showAndWait();
                                        }),
                        throwable -> {
                            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.FALLO_AL_REALIZAR_LA_PETICION);
                            alert.showAndWait();
                        }
                );


        root.getRoot().setCursor(Cursor.WAIT);

    }


}
