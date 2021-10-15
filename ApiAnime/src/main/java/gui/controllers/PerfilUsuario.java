package gui.controllers;

import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.Move;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.ResultsItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import servicios.ServiciosPokemon;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PerfilUsuario implements Initializable {

    @FXML
    private ListView<Movimiento> listViewDatosMovimiento;
    @FXML
    private TextField textFieldDatos;
    @FXML
    private ImageView imageView;
    @FXML
    private ListView<String> listViewMovimientos;
    @FXML
    private ComboBox comboBoxPokemones;
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
        String p = textFieldDatos.getText();
        listViewMovimientos.getItems().clear();
        //  listViewMovimientos.getItems().addAll(serviciosPokemon.getAllPokemon()
        //          .stream().map(resultsItem -> resultsItem.getName()).collect(Collectors.toList()));


    }

    @FXML
    private void buscarPokemon(ActionEvent actionEvent) {
        String p = textFieldDatos.getText();
        listViewMovimientos.getItems().clear();
        listViewMovimientos.getItems().addAll(
                serviciosPokemon.getMovimientosPorId(p)
                        .stream()
                        .map(movesItem -> movesItem.getMove().getName())
                        .collect(Collectors.toList()));

        Image image = new Image(serviciosPokemon.getDatosByNombre(p).getSprites().getFrontDefault());
        imageView.setImage(image);
    }

    @FXML
    private void seleccionarDatos() {
        listViewMovimientos.getItems().clear();
        listViewMovimientos.getItems().addAll(
                serviciosPokemon.getDatosByNombre(
                        comboBoxPokemones.getValue().toString())
                        .getMovimientos()
                        .stream()
                        .map(MovesItem::getMove)
                        .map(Move::getName)
                        .collect(Collectors.toList()));

        Image image = new Image(serviciosPokemon.getDatosByNombre(
                comboBoxPokemones.getValue().toString()).getSprites().getFrontDefault());
        imageView.setImage(image);
    }

    @FXML
    private void cargarDatos(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            listViewDatosMovimiento.getItems().clear();
            listViewDatosMovimiento.getItems().addAll(serviciosPokemon.getDatosMovimiento(
                    listViewMovimientos.getSelectionModel().getSelectedItem()));
        }
    }
}
