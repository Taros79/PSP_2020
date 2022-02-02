package org.example.ModuloCliente.gui.controllers;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.Common.EE.utils.HashPassword;
import org.example.ModuloCliente.servicios.ServiciosUsuario;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class IniciarSesion implements Initializable {

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPass;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private ServiciosUsuario serviciosUsuario;
    private HashPassword hash = new HashPassword();

    @Inject
    public IniciarSesion(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    @FXML
    private void hacerLogin() {
        if (!textFieldNombre.getText().isEmpty() && !textFieldPass.getText().isEmpty()) {
            Single<String> s = Single.fromCallable(() -> serviciosUsuario.login(textFieldNombre.getText(), textFieldPass.getText()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal
                            .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
            s.subscribe((s1 -> {
                        var u = serviciosUsuario.getUsuarioLogin(textFieldNombre.getText());
                        if (u.isRight()) {
                            if (u.get().getTipoUsuario() == 1) {
                                pantallaPrincipal.activarAdmin();
                            } else if (u.get().getTipoUsuario() == 2) {
                                pantallaPrincipal.activarUser();
                            }
                        }
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
    }
}