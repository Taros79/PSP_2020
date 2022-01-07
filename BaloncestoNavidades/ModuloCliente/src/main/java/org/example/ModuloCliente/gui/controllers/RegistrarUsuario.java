package org.example.ModuloCliente.gui.controllers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.TipoUsuario;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.gui.utils.Constantes;
import org.example.ModuloCliente.servicios.ServiciosUsuario;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrarUsuario implements Initializable {

    @FXML
    private ComboBox<TipoUsuario> comboBoxTipoUser;
    @FXML
    private ListView<Usuario> listViewAutores;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtContraseña;
    @FXML
    private TextField txtCorreo;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private ServiciosUsuario serviciosUsuario;
    private HashPassword hash = new HashPassword();

    @Inject
    public RegistrarUsuario(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewAutores.getSelectionModel().getSelectedItem() != null) {
            if (mouseEvent.getClickCount() == 1 && listViewAutores.getSelectionModel().getSelectedItem() != null) {

            }
        }
    }

    @FXML
    private void addUsuario() {
        if (!txtNombre.getText().isEmpty() && !txtContraseña.getText().isEmpty()
                && !txtCorreo.getText().isEmpty()) {
            String pass = hash.hashPassword(txtContraseña.getText());
            UsuarioRegistro u = new UsuarioRegistro(txtCorreo.getText(), txtNombre.getText(), pass, "", 0, LocalDateTime.now(), 2);

            addUsuarioTask(u);
            serviciosUsuario.mandarMail("promocarlos1.2@gmail.com", u.getUsername());
        } else {
            a.setContentText(Constantes.ALGUN_CAMPO_VACIO);
            a.showAndWait();
        }
    }

    private void addUsuarioTask(UsuarioRegistro u) {
        @NonNull Single<Either<ApiError, UsuarioRegistro>> s = Single.fromCallable(() ->
                        serviciosUsuario.addUsuarioRegistro(u))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
        s.subscribe(result ->
                        result.peek(usuario -> {
                                    a.setContentText(Constantes.USUARIO_OK);
                                    a.showAndWait();
                                })
                                .peekLeft(error -> {
                                    mensajeAlert(u);
                                }),
                throwable -> {
                    a.setContentText(throwable.getMessage());
                    a.showAndWait();
                });
        this.pantallaPrincipal
                .getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    private void mensajeAlert(UsuarioRegistro u) {
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setTitle(Constantes.MENSAJE_CONFIRMACION);
        a.setHeaderText(Constantes.REGISTRO_TXT1);
        a.setContentText(Constantes.REGISTRO_TXT2);

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() != ButtonType.OK) {
            serviciosUsuario.mandarMail("promocarlos1.2@gmail.com", u.getUsername());
            a.setHeaderText(Constantes.REGISTRO_TXT3);
            a.setContentText(Constantes.REGISTRO_TXT4);
            a.showAndWait();
        }
    }

    public void actualizar(){
       /* txtNombre.clear();
        txtContraseña.clear();
        txtCorreo.clear();
        listViewAutores.getItems().clear();
        listViewAutores.getItems().addAll(serviciosUsuario.getAllUsuario().get());*/
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }
}
