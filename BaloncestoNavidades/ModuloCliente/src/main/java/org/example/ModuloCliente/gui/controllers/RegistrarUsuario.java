package org.example.ModuloCliente.gui.controllers;

import com.github.javafaker.Faker;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.DaoUsuario;
import org.example.ModuloCliente.dao.retrofit.BaloncestoApi;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrarUsuario implements Initializable {

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
    private void rellenarFaker(ActionEvent actionEvent) {
        Faker f = new Faker();
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

        System.out.println(daoUsuario.getCorreo("promocarlos1.2@gmail.com"));

 /*    if (!txtNombre.getText().isEmpty() && !txtContraseña.getText().isEmpty()
                && !txtCorreo.getText().isEmpty()) {
            String pass = hash.hashPassword(txtContraseña.getText());
            daoUsuario.addUsuarioRegistro(
                    new UsuarioRegistro(txtCorreo.getText(), txtNombre.getText(), pass, 2));
            daoUsuario.getCorreo(txtCorreo.getText());
        } else {
            a.setContentText("Algun campo esta vacio");
            a.showAndWait();
        }*/
    }

    @FXML
    private void borrarAutor(ActionEvent actionEvent) {
    }

    @FXML
    private void modificarAutor(ActionEvent actionEvent) {
    }

    public void actualizar() {
        txtNombre.clear();
        txtContraseña.clear();
        listViewAutores.getItems().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }
}
