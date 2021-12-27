package org.example.ModuloCliente.gui.controllers;

import com.github.javafaker.Faker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.Common.modelo.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class CrearUsuario implements Initializable {

    @FXML
    private ListView<Usuario> listViewAutores;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

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
    private void addAutor(ActionEvent actionEvent) {
        if (!txtNombre.getText().isEmpty() && !txtApellidos.getText().isEmpty()) {
            actualizar();
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
        txtApellidos.clear();
        listViewAutores.getItems().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }
}
