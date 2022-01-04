package org.example.ModuloCliente.gui.controllers;

import com.github.javafaker.Faker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.DaoUsuario;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
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
        if (!txtNombre.getText().isEmpty() && !txtContraseña.getText().isEmpty()
                && !txtCorreo.getText().isEmpty()) {
            String pass = hash.hashPassword(txtContraseña.getText());
            UsuarioRegistro u = new UsuarioRegistro(txtCorreo.getText(), txtNombre.getText(), pass, LocalDateTime.now(),2);
            daoUsuario.addUsuarioRegistro(u);
            System.out.println(daoUsuario.mandarMail("promocarlos1.2@gmail.com", u.getUsername()));
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
        } else {
            a.setContentText("Algun campo esta vacio");
            a.showAndWait();
        }
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
