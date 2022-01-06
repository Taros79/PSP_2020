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
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.TipoUsuario;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.DaoUsuario;
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

    private DaoUsuario daoUsuario;
    private HashPassword hash = new HashPassword();

    @Inject
    public RegistrarUsuario(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
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

            System.out.println(daoUsuario.mandarMail("promocarlos1.2@gmail.com", u.getUsername()));
           /* a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setTitle("MENSAJE CONFIRMACION");
            a.setHeaderText("Ahora debes ir al correo que proporcionastes y " +
                    "aceptar el codigo de validacion antes de logearte.");
            a.setContentText("Te llego el mensaje de confirmacion al correo?");

            Optional<ButtonType> result = a.showAndWait();
            if (result.get() != ButtonType.OK) {
                daoUsuario.mandarMail("promocarlos1.2@gmail.com", u.getUsername());
                a.setHeaderText("Reintento de envio");
                a.setContentText("Volvimos a enviarte tu correo por favor confirme " +
                        "que no se encuentra en su bandeja de spam");
                a.showAndWait();
            }*/
        } else {
            a.setContentText("Algun campo esta vacio");
            a.showAndWait();
        }
    }

    private void addUsuarioTask(UsuarioRegistro u) {
        @NonNull Single<Either<ApiError, UsuarioRegistro>> s = Single.fromCallable(() ->
                        daoUsuario.addUsuarioRegistro(u))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
        s.subscribe(result ->
                        result.peek(usuario -> {
                                    mensajeAlert(u);
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

    private void mensajeAlert(UsuarioRegistro u) {
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setTitle("MENSAJE CONFIRMACION");
        a.setHeaderText("Ahora debes ir al correo que proporcionastes y " +
                "aceptar el codigo de validacion antes de logearte.");
        a.setContentText("Te llego el mensaje de confirmacion al correo?");

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() != ButtonType.OK) {
            daoUsuario.mandarMail("promocarlos1.2@gmail.com", u.getUsername());
            a.setHeaderText("Reintento de envio");
            a.setContentText("Volvimos a enviarte tu correo por favor confirme " +
                    "que no se encuentra en su bandeja de spam");
            a.showAndWait();
        }
    }


    @FXML
    private void borrarUsuario() {
        @NonNull Single<Either<ApiError, ApiRespuesta>> s = Single.fromCallable(() ->
                        daoUsuario.deleteUsuario(listViewAutores.getSelectionModel().getSelectedItem().getUsername()))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
        s.subscribe(result ->
                        result.peek(usuario -> {

                                    a.setContentText("Usuario borrado con exito");
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

    public void actualizar(){
        txtNombre.clear();
        txtContraseña.clear();
        txtCorreo.clear();
        listViewAutores.getItems().clear();
        listViewAutores.getItems().addAll(daoUsuario.getAllUsuario().get());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }
}
