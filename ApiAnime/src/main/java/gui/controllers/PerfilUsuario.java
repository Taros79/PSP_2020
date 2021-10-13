package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import servicios.ServiciosPokemon;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilUsuario implements Initializable {

    @FXML
    private ListView listViewPersonajes;
    @FXML
    private ComboBox comboBoxPerfil;
    @FXML
    private TextField textFieldDatos;
    @FXML
    private TextArea textAreaPerfil;
    @FXML
    private PantallaPrincipal pantallaPrincipal;
    private Alert a;
    ServiciosPokemon serviciosPokemon = new ServiciosPokemon();


    public void setBorderPane(PantallaPrincipal borderPane) {
        this.pantallaPrincipal = borderPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
        comboBoxPerfil.getItems().addAll("NOMBRE", "TELEFONO");
    }

    @FXML
    private void editarPerfil(ActionEvent actionEvent) {

    }

    @FXML
    private void borrarCuenta(ActionEvent actionEvent) {
        listViewPersonajes.getItems().addAll(serviciosPokemon.getAll());
    }
}
