package gui.controllers;

import dao.modelo.ModPokemon.Move;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.ResultsItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import servicios.ServiciosPokemon;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PerfilUsuario implements Initializable {

    @FXML
    private ListView<String> listViewPersonajes;
    @FXML
    private ComboBox comboBoxPokemones;
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
        comboBoxPokemones.getItems().addAll(serviciosPokemon.getAllPokemon()
                .stream().map(ResultsItem::getName).collect(Collectors.toList()));
    }

    @FXML
    private void editarPerfil(ActionEvent actionEvent) {
        listViewPersonajes.getItems().clear();
        //  listViewPersonajes.getItems().addAll(serviciosPokemon.getAllPokemon()
        //          .stream().map(resultsItem -> resultsItem.getName()).collect(Collectors.toList()));
    }

    @FXML
    private void borrarCuenta(ActionEvent actionEvent) {
        listViewPersonajes.getItems().clear();
        // listViewPersonajes.getItems().addAll(serviciosPokemon.getDatosByNombre(textFieldDatos.getText()));
    }

    @FXML
    private void seleccionarDatos() {
        listViewPersonajes.getItems().clear();
        listViewPersonajes.getItems().addAll(
                serviciosPokemon.getDatosByNombre(
                        comboBoxPokemones.getValue().toString())
                        .getMovimientos()
                        .stream()
                        .map(MovesItem::getMove)
                        .map(Move::getName)
                        .collect(Collectors.toList()));
    }
}
