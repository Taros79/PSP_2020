package gui.controllers;

import dao.modelo.Pokemon;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import servicios.serviciosImplementacion.ServiciosPokemonImpl;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PerfilPokemon implements Initializable {

    ServiciosPokemonImpl serviciosPokemonImpl;
    @FXML
    private TextArea labelDefinicion;
    @FXML
    private Label labelMovimiento;
    @FXML
    private ListView<String> listViewDatosMovimiento;
    @FXML
    private TextField textFieldDatos;
    @FXML
    private ImageView imageView;
    @FXML
    private ListView<Pokemon> listViewMovimientos;
    @FXML
    private ComboBox<String> comboBoxPokemones;
    @FXML
    private PantallaPrincipal pantallaPrincipal;
    private Alert a;

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Inject
    public PerfilPokemon(ServiciosPokemonImpl serviciosPokemonImpl) {
        this.serviciosPokemonImpl = serviciosPokemonImpl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
        if (serviciosPokemonImpl.getAllPokemon().isRight()) {
            comboBoxPokemones.getItems().addAll(serviciosPokemonImpl.getAllPokemon()
                    .get()
                    .stream().map(Pokemon::getName)
                    .collect(Collectors.toList()));
        } else {
            a.setContentText(serviciosPokemonImpl.getAllPokemon().getLeft());
            a.showAndWait();
        }
    }

    @FXML
    private void buscarPokemon() {
       listViewMovimientos.getItems().clear();
       listViewMovimientos.getItems().addAll(serviciosPokemonImpl.getDatosByNombre(textFieldDatos.getText()).get());
    }


    @FXML
    private void seleccionarDatos() {

    }


    @FXML
    private void cargarDatos(MouseEvent mouseEvent) {
    }
}
