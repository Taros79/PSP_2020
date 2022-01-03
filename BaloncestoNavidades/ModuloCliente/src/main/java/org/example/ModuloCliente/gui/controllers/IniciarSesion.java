package org.example.ModuloCliente.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.ModuloCliente.dao.DaoUsuario;

import javax.inject.Inject;
import java.net.URL;
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
            var user = daoUsuario.getUsuarioLogin(new UsuarioLoginDTO(textFieldNombre.getText(), textFieldPass.getText()));

            if (user.isRight()) {
                String pass = hash.hashPassword(textFieldPass.getText());
                if(Objects.equals(user.get().getHashedPassword(), pass)){
                    System.out.println("ok");
                }else{
                    System.out.println("penne");
                }
            }else{
                System.out.println("penne");
            }


        }

       /* UsuarioRegistro u = new UsuarioRegistro("carlos79@gmail.com", "amanterechoncho79", "amanterechoncho", 1);
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