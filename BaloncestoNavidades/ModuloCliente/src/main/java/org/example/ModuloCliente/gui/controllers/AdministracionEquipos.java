package org.example.ModuloCliente.gui.controllers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Equipo;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.dao.DaoPartidos;
import org.example.ModuloCliente.gui.utils.Constantes;
import org.example.ModuloCliente.servicios.ServiciosEquipo;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdministracionEquipos implements Initializable {

    @FXML
    private TextField textFieldNombre;
    @FXML
    private ListView<Equipo> listViewEquipos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private ServiciosEquipo serviciosEquipo;

    @Inject
    public AdministracionEquipos(ServiciosEquipo serviciosEquipo) {
        this.serviciosEquipo = serviciosEquipo;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewEquipos.getSelectionModel().getSelectedItem() != null) {
            if (mouseEvent.getClickCount() == 1 && listViewEquipos.getSelectionModel().getSelectedItem() != null) {
                textFieldNombre.setText(listViewEquipos.getSelectionModel().getSelectedItem().getNombre());
            }
        }
    }

    public void actualizar() {
        textFieldNombre.clear();
        listViewEquipos.getItems().clear();
        listViewEquipos.getItems().addAll(serviciosEquipo.getAllEquipos().get());
    }

    @FXML
    private void AddEquipo() {

        if (!Objects.equals(textFieldNombre.getText(), "")) {
            @NonNull Single<Either<ApiError, Equipo>> s = Single.fromCallable(() ->
                            serviciosEquipo.addEquipo(new Equipo(textFieldNombre.getText())))
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
    private void borrarEquipo() {

        if (listViewEquipos.getSelectionModel().getSelectedItem() != null) {
            @NonNull Single<Either<ApiError, ApiRespuesta>> s = Single.fromCallable(() ->
                            serviciosEquipo.deleteEquipo(listViewEquipos.getSelectionModel().getSelectedItem().getNombre()))
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
    private void ActualizarEquipo() {

        if (listViewEquipos.getSelectionModel().getSelectedItem() != null &&
                textFieldNombre.getText() != null) {
            var id = listViewEquipos.getSelectionModel().getSelectedItem().getIdEquipo();
            @NonNull Single<Either<ApiError, ApiRespuesta>> s = Single.fromCallable(() ->
                            serviciosEquipo.updateEquipo(new Equipo(id, textFieldNombre.getText())))
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
