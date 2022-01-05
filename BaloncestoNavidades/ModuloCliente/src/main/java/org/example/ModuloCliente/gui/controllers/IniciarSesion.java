package org.example.ModuloCliente.gui.controllers;

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

    public void actualizar() {
    }

    @FXML
    private void añadir() {
        if (!textFieldPass.getText().isEmpty() && !textFieldNombre.getText().isEmpty()) {
            var user = daoUsuario.getUsuarioLogin(new UsuarioLoginDTO(textFieldNombre.getText(), textFieldPass.getText(), 0));

            if (user.isRight()) {
                String pass = hash.hashPassword(textFieldPass.getText());
                if(Objects.equals(user.get().getHashedPassword(), pass)){
                   if (user.get().getIsActivo()==1){
                       System.out.println("ok");
                   }else{
                       daoUsuario.deleteUsuario(user.get().getUsername());
                   }
                }else{
                    System.out.println("Usuario no valido");
                }
            }else{
                System.out.println("Error en la bbdd");
            }

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
}