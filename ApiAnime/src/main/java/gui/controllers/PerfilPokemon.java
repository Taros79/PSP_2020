package gui.controllers;

import dao.modelo.ModMovimientos.FlavorTextEntriesItem;
import dao.modelo.ModPokemon.Move;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import servicios.ServiciosPokemon;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PerfilPokemon implements Initializable {

    ServiciosPokemon serviciosPokemon;
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
    private ListView<String> listViewMovimientos;
    @FXML
    private ComboBox<String> comboBoxPokemones;
    @FXML
    private PantallaPrincipal pantallaPrincipal;
    private Alert a;

    public void setBorderPane(PantallaPrincipal borderPane) {
        this.pantallaPrincipal = borderPane;
    }

    @Inject
    public PerfilPokemon(ServiciosPokemon serviciosPokemon) {
        this.serviciosPokemon = serviciosPokemon;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
        if (serviciosPokemon.getAllPokemon().isRight()) {
            comboBoxPokemones.getItems().addAll(serviciosPokemon.getAllPokemon()
                    .get()
                    .stream().map(Pokemon::getNombre)
                    .collect(Collectors.toList()));
        } else {
            a.setContentText(serviciosPokemon.getAllPokemon().getLeft());
            a.showAndWait();
        }
    }

    @FXML
    private void buscarPokemon() {
        String pokemon = textFieldDatos.getText();
        if (serviciosPokemon.getMovimientosPorId(pokemon).isRight()) {
            listViewMovimientos.getItems().clear();
            listViewMovimientos.getItems().addAll(
                    serviciosPokemon.getMovimientosPorId(pokemon).get()
                            .stream()
                            .map(movesItem -> movesItem.getMove().getName())
                            .collect(Collectors.toList()));

            Image image = new Image(serviciosPokemon.getDatosByNombre(pokemon).get().getSprites().getFrontDefault());
            imageView.setImage(image);
        } else {
            listViewMovimientos.getItems().clear();
            Image image = new Image("https://i.pinimg.com/originals/19/23/da/1923da24d71bc552b067ee76b93cf15e.jpg");
            imageView.setImage(image);
            a.setContentText(serviciosPokemon.getMovimientosPorId(pokemon).getLeft());
            a.showAndWait();
        }
    }

    @FXML
    private void seleccionarDatos() {
        listViewMovimientos.getItems().clear();
        listViewMovimientos.getItems().addAll(
                serviciosPokemon.getDatosByNombre(
                                comboBoxPokemones.getValue()).get()
                        .getMovimientos()
                        .stream()
                        .map(MovesItem::getMove)
                        .map(Move::getName)
                        .collect(Collectors.toList()));

        Image image = new Image(serviciosPokemon.getDatosByNombre(
                comboBoxPokemones.getValue()).get().getSprites().getFrontDefault());
        imageView.setImage(image);
    }


    @FXML
    private void cargarDatos(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewMovimientos.getSelectionModel().getSelectedItem() != null) {
            listViewDatosMovimiento.getItems().clear();
            listViewDatosMovimiento.getItems().addAll(
                    serviciosPokemon.getDatosMovimiento(listViewMovimientos.getSelectionModel()
                            .getSelectedItem()).getName());

            labelMovimiento.setText(listViewMovimientos.getSelectionModel().getSelectedItem());
            labelDefinicion.setText(serviciosPokemon.getDatosMovimiento(
                            listViewMovimientos.getSelectionModel().getSelectedItem()).getFlavorTextEntries()
                    .stream().filter(flavorTextEntriesItem -> flavorTextEntriesItem.getLanguage().getName().equals("es"))
                    .filter(flavorTextEntriesItem -> flavorTextEntriesItem.getVersionGroup().getName().equals("x-y"))
                    .map(FlavorTextEntriesItem::getFlavorText).collect(Collectors.joining()));

        }
    }
}
