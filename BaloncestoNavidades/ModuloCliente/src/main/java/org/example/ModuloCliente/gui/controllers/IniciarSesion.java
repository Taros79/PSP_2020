package org.example.ModuloCliente.gui.controllers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.DaoUsuario;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class IniciarSesion implements Initializable {

    @FXML
    private ListView<Usuario> listViewUsuarios;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPass;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private DaoUsuario daoUsuario;
    private HashPassword hash = new HashPassword();

    @Inject
    public IniciarSesion(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    @FXML
    private void añadir() {
        if (!textFieldNombre.getText().isEmpty() && !textFieldPass.getText().isEmpty()) {
            Single<String> s = Single.fromCallable(() -> daoUsuario.login(textFieldNombre.getText(), textFieldPass.getText()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal
                            .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
            s.subscribe((s1 -> {
                        a.setContentText(s1);
                        a.showAndWait();
                    }),
                    throwable -> {
                        a.setContentText(throwable.getMessage());
                        a.showAndWait();
                    });
            this.pantallaPrincipal
                    .getPantallaPrincipal().setCursor(Cursor.WAIT);
        }

      /*  UsuarioRegistro u = new UsuarioRegistro("carlos79@gmail.com", "amanterechoncho79", "amanterechoncho", LocalDateTime.now(),1);
        System.out.println(daoUsuario.addUsuarioRegistro(u));

        if(daoUsuario.getAllUsuario() != null){
            Single<Either<ApiError, List<Usuario>>> s = Single.fromCallable(() -> daoUsuario.getAllUsuario())
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal
                            .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
            s.subscribe(result ->
                            result.peek(usuarios ->
                                            listViewUsuarios.getItems().addAll(usuarios))
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
        }else{
            System.out.println("no mames");
        }*/
    }

    private void deleteUsuarioTask(UsuarioLoginDTO u) {
        @NonNull Single<Either<ApiError, ApiRespuesta>> s = Single.fromCallable(() ->
                daoUsuario.deleteUsuario(u.getUsername()))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
        s.subscribe(result ->
                        result.peek(usuario -> {
                                    a.setContentText("Bien");
                                    a.showAndWait();
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
    }
}