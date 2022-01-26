package org.example.ModuloCliente.gui.controllers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.gui.utils.Constantes;
import org.example.ModuloCliente.servicios.ServiciosEquipo;
import org.example.ModuloCliente.servicios.ServiciosJornada;
import org.example.ModuloCliente.servicios.ServiciosPartido;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministracionPartidos implements Initializable {

    @FXML
    private ListView<Jornada> listViewJornadas;
    @FXML
    private ComboBox<Equipo> comboLocal;
    @FXML
    private ComboBox<Equipo> comboVisitante;
    @FXML
    private TextField textLocal;
    @FXML
    private TextField textVisitante;
    @FXML
    private ListView<Partido> listViewPartidos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private ServiciosPartido serviciosPartido;
    private ServiciosJornada serviciosJornada;
    private ServiciosEquipo serviciosEquipo;

    @Inject
    public AdministracionPartidos(ServiciosPartido serviciosPartido, ServiciosJornada serviciosJornada, ServiciosEquipo serviciosEquipo) {
        this.serviciosPartido = serviciosPartido;
        this.serviciosJornada = serviciosJornada;
        this.serviciosEquipo = serviciosEquipo;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);

        textVisitante.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textVisitante.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        textLocal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textLocal.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }


    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewPartidos.getSelectionModel().getSelectedItem() != null) {
            if (mouseEvent.getClickCount() == 1 && listViewPartidos.getSelectionModel().getSelectedItem() != null) {
                textLocal.setText(String.valueOf(listViewPartidos.getSelectionModel().getSelectedItem().getResultadoLocal()));
                textVisitante.setText(String.valueOf(listViewPartidos.getSelectionModel().getSelectedItem().getResultadoVisitante()));
            }
        }
    }

    public void actualizar() {
        listViewPartidos.getItems().clear();
        listViewPartidos.getItems().addAll(serviciosPartido.getAllPartido().get());
        listViewJornadas.getItems().clear();
        listViewJornadas.getItems().addAll(serviciosJornada.getAllJornadas().get());
        comboLocal.getItems().clear();
        comboLocal.getItems().addAll(serviciosEquipo.getAllEquipos().get());
        comboVisitante.getItems().clear();
        comboVisitante.getItems().addAll(serviciosEquipo.getAllEquipos().get());
        textVisitante.clear();
        textLocal.clear();
    }

    @FXML
    private void actualizarPartido() {
        if (listViewPartidos.getSelectionModel().getSelectedItem() != null &&
                listViewJornadas.getSelectionModel().getSelectedItem() != null &&
                comboVisitante.getSelectionModel().getSelectedItem() != null &&
                comboLocal.getSelectionModel().getSelectedItem() != null &&
                textLocal.getText() != null && textVisitante.getText() != null) {
            var id = listViewPartidos.getSelectionModel().getSelectedItem().getIdPartido();
            @NonNull Single<Either<ApiError, ApiRespuesta>> s = Single.fromCallable(() ->
                            serviciosPartido.updatePartido(new Partido(
                                    id,
                                    listViewJornadas.getSelectionModel().getSelectedItem().getId(),
                                    comboLocal.getSelectionModel().getSelectedItem().getIdEquipo(),
                                    comboVisitante.getSelectionModel().getSelectedItem().getIdEquipo(),
                                    Integer.parseInt(textLocal.getText()),
                                    Integer.parseInt(textVisitante.getText()))))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal
                            .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
            s.subscribe(result ->
                            result.peek(apiRespuesta -> {
                                        a.setContentText(apiRespuesta.getMessage());
                                        a.showAndWait();
                                        actualizar();
                                    })
                                    .peekLeft(error -> {
                                        a.setContentText(error.getMessage());
                                        a.showAndWait();
                                    }),
                    throwable -> {
                        a.setContentText(throwable.getMessage());
                        a.showAndWait();
                    });
            this.pantallaPrincipal
                    .getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(Constantes.CAMPO_INCOMPLETO);
            a.showAndWait();
        }
    }

    @FXML
    private void borrarPartido() {
        if (listViewPartidos.getSelectionModel().getSelectedItem() != null) {
            @NonNull Single<Either<ApiError, ApiRespuesta>> s = Single.fromCallable(() ->
                            serviciosPartido.deletePartido(String.valueOf(listViewPartidos.getSelectionModel().getSelectedItem().getIdPartido())))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal
                            .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
            s.subscribe(result ->
                            result.peek(apiRespuesta -> {
                                        a.setContentText(apiRespuesta.getMessage());
                                        a.showAndWait();
                                        actualizar();
                                    })
                                    .peekLeft(error -> {
                                        a.setContentText(error.getMessage());
                                        a.showAndWait();
                                    }),
                    throwable -> {
                        a.setContentText(throwable.getMessage());
                        a.showAndWait();
                    });
            this.pantallaPrincipal
                    .getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(Constantes.CAMPO_INCOMPLETO);
            a.showAndWait();
        }
    }


    @FXML
    private void addPartido() {
        if (listViewPartidos.getSelectionModel().getSelectedItem() != null &&
                listViewJornadas.getSelectionModel().getSelectedItem() != null &&
                comboVisitante.getSelectionModel().getSelectedItem() != null &&
                comboLocal.getSelectionModel().getSelectedItem() != null &&
                textLocal.getText() != null && textVisitante.getText() != null) {
            @NonNull Single<Either<ApiError, Partido>> s = Single.fromCallable(() ->
                            serviciosPartido.addPartido(new Partido(
                                    listViewJornadas.getSelectionModel().getSelectedItem().getId(),
                                    comboLocal.getSelectionModel().getSelectedItem().getIdEquipo(),
                                    comboVisitante.getSelectionModel().getSelectedItem().getIdEquipo(),
                                    Integer.parseInt(textLocal.getText()),
                                    Integer.parseInt(textVisitante.getText()))))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal
                            .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
            s.subscribe(result ->
                            result.peek(apiRespuesta -> {
                                        actualizar();
                                    })
                                    .peekLeft(error -> {
                                        a.setContentText(error.getMessage());
                                        a.showAndWait();
                                    }),
                    throwable -> {
                        a.setContentText(throwable.getMessage());
                        a.showAndWait();
                    });
            this.pantallaPrincipal
                    .getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(Constantes.CAMPO_INCOMPLETO);
            a.showAndWait();
        }
    }
}
