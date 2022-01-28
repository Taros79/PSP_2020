package org.example.ModuloCliente.gui.controllers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Jornada;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.gui.utils.Constantes;
import org.example.ModuloCliente.servicios.ServiciosJornada;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdministracionJornadas implements Initializable {
    @FXML
    private DatePicker datePicker;
    @FXML
    private ListView<Jornada> listViewJornadas;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private ServiciosJornada serviciosJornada;

    @Inject
    public AdministracionJornadas(ServiciosJornada serviciosJornada) {
        this.serviciosJornada = serviciosJornada;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    public void actualizar() {
        if (listViewJornadas.getItems() != null) {
            @NonNull Single<Either<ApiError, List<Jornada>>> s = Single.fromCallable(() ->
                            serviciosJornada.getAllJornadas())
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal
                            .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
            s.subscribe(result ->
                            result.peek(apiRespuesta -> {
                                        listViewJornadas.getItems().clear();
                                        listViewJornadas.getItems().addAll(result.get());
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
    private void AddJornada() {

        if (datePicker.getValue() != null) {
            @NonNull Single<Either<ApiError, Jornada>> s = Single.fromCallable(() ->
                            serviciosJornada.addJornada(new Jornada(datePicker.getValue().atStartOfDay())))
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

    @FXML
    private void borrarJornada() {

        if (listViewJornadas.getSelectionModel().getSelectedItem() != null) {
            @NonNull Single<Either<ApiError, ApiRespuesta>> s = Single.fromCallable(() ->
                            serviciosJornada.deleteJornada(String.valueOf(listViewJornadas.getSelectionModel().getSelectedItem().getId())))
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
    private void actualizarJornada() {

        if (listViewJornadas.getSelectionModel().getSelectedItem() != null &&
                datePicker.getValue() != null) {
            var id = listViewJornadas.getSelectionModel().getSelectedItem().getId();
            @NonNull Single<Either<ApiError, ApiRespuesta>> s = Single.fromCallable(() ->
                            serviciosJornada.updateJornada(new Jornada(id, datePicker.getValue().atStartOfDay())))
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
}
