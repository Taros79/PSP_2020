package quevedo.ClientBasket.ui.controllers.gestionar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import quevedo.ClientBasket.services.GestionService;
import quevedo.ClientBasket.services.VisualizarServices;
import quevedo.ClientBasket.ui.controllers.MainController;
import quevedo.ClientBasket.utils.ConstantesMensajes;
import quevedo.common.modelo.dto.EquipoDTO;
import quevedo.common.modelo.dto.JornadaDTO;
import quevedo.common.modelo.dto.PartidoDTO;

import javax.inject.Inject;

public class AddController {

    private final GestionService gestionService;
    private final VisualizarServices visualizarServices;
    @FXML
    private DatePicker dpJornada;
    @FXML
    private TextField taNombreEquipo;
    @FXML
    private ListView<EquipoDTO> lvEquiposLocales;
    @FXML
    private ListView<EquipoDTO> lvEquiposVisitantes;
    @FXML
    private ListView<JornadaDTO> lvJornadas;
    @FXML
    private TextField taResultadoLocal;
    @FXML
    private TextField taResultadoVisitante;
    private Alert alert;

    private MainController root;

    @Inject
    public AddController(GestionService gestionService, VisualizarServices visualizarServices) {
        this.gestionService = gestionService;
        this.visualizarServices = visualizarServices;
    }

    public void setRoot(MainController root) {
        this.root = root;
    }

    @FXML
    private void addEquipo(ActionEvent actionEvent) {

        if (taNombreEquipo.getText().isBlank()) {
            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.INTRODUZCA_EL_NOMBRE_DEL_EQUIPO);
            alert.show();
        } else {
            gestionService.addEquipo(new EquipoDTO(taNombreEquipo.getText(), 0))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(equipo -> {
                                                        lvEquiposVisitantes.getItems().add(equipo);
                                                        lvEquiposLocales.getItems().add(equipo);
                                                        taNombreEquipo.clear();

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

    @FXML
    private void addJornada(ActionEvent actionEvent) {


        if (dpJornada.getValue() == null) {
            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.INTRODUZCA_EL_DIA_DE_LA_JORNADA);
            alert.show();
        } else {
            gestionService.addJornada(new JornadaDTO(dpJornada.getValue(), 0))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(jornadaDTO -> {
                                                        lvJornadas.getItems().add(jornadaDTO);
                                                        dpJornada.setValue(null);

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


    @FXML
    private void addPartido(ActionEvent actionEvent) {


        if (lvEquiposLocales.getSelectionModel().getSelectedItem() == null || lvEquiposVisitantes.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.SELECCIONA_UN_EQUIPO_LOCAL_Y_UNO_VISITANTE);
            alert.show();
        } else if (lvJornadas.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.SELECCIONA_UNA_JORNADA);
            alert.show();
        } else if (taResultadoLocal.getText().isBlank() || taResultadoVisitante.getText().isBlank()) {
            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.INTRODUZCA_LOS_PUNTOS_PARA_EL_EQUIPO_VISITANTE_Y_EL_EQUIPO_LOCAL);
            alert.show();
        } else if (lvEquiposLocales.getSelectionModel().getSelectedItem().equals(lvEquiposVisitantes.getSelectionModel().getSelectedItem())) {
            alert = new Alert(Alert.AlertType.ERROR, ConstantesMensajes.NO_PUEDES_SELECCIONAR_EL_MISMO_EQUIPO_EN_LOCAL_Y_VISITANTE);
            alert.show();
        } else {
            PartidoDTO partidoNuevo = new PartidoDTO(lvJornadas.getSelectionModel().getSelectedItem().getId(),
                    lvEquiposLocales.getSelectionModel().getSelectedItem().getId(),
                    lvEquiposVisitantes.getSelectionModel().getSelectedItem().getId(),
                    Integer.parseInt(taResultadoLocal.getText()),
                    Integer.parseInt(taResultadoVisitante.getText()));
            gestionService.addPartido(partidoNuevo)
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(mesnaje -> {
                                                        alert = new Alert(Alert.AlertType.INFORMATION, mesnaje.getMensaje());
                                                        alert.show();
                                                        taNombreEquipo.clear();

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

    public void load() {
        loadEquipos();
        loadJornadas();

    }


    private void loadEquipos() {
        visualizarServices.allEquipos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(listado -> {
                                                    lvEquiposVisitantes.getItems().clear();
                                                    lvEquiposLocales.getItems().clear();
                                                    lvEquiposLocales.getItems().addAll(listado);
                                                    lvEquiposVisitantes.getItems().addAll(listado);

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


    private void loadJornadas() {

        visualizarServices.allJornadas()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.root.getRoot().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(listado -> {
                                                    lvJornadas.getItems().clear();
                                                    lvJornadas.getItems().addAll(listado);

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
