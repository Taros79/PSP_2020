package gui.controllers;

import dao.modelo.ModMovimientos.FlavorTextEntriesItem;
import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.Move;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
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
    private ListView<String> listViewMovimientos;
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
                    .stream().map(Pokemon::getNombre)
                    .collect(Collectors.toList()));
        } else {
            a.setContentText(serviciosPokemonImpl.getAllPokemon().getLeft());
            a.showAndWait();
        }
    }

    @FXML
    private void buscarPokemon() {
        String pokemon = textFieldDatos.getText();
        var tarea = new Task<Either<String, List<MovesItem>>>() {
            @Override
            protected Either<String, List<MovesItem>> call() throws Exception {
                return serviciosPokemonImpl.getMovimientosPorId(pokemon);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            listViewMovimientos.getItems().clear();
            Try.of(() -> tarea.get()
                            .peek(movesItems -> {
                                listViewMovimientos.getItems().addAll(movesItems.stream()
                                        .map(movesItem -> movesItem.getMove().getName())
                                        .collect(Collectors.toList()));
                                imageView.setImage(new Image(serviciosPokemonImpl
                                        .getDatosByNombre(pokemon).get()
                                        .getSprites()
                                        .getFrontDefault()));
                            })
                            .peekLeft(s -> {
                                imageView.setImage(new Image("https://i.pinimg.com/originals/19/23/da/1923da24d71bc552b067ee76b93cf15e.jpg"));
                                a.setContentText(s);
                                a.showAndWait();
                            }))
                    .onFailure(throwable -> {
                        a.setContentText(throwable.getMessage());
                        a.showAndWait();
                    });
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        tarea.setOnFailed(workerStateEvent -> {
            a.setContentText(workerStateEvent.getSource().getException().getMessage());
            a.showAndWait();
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        new Thread(tarea).start();
        this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }


    @FXML
    private void seleccionarDatos() {
        listViewMovimientos.getItems().clear();
        listViewMovimientos.getItems().addAll(
                serviciosPokemonImpl.getDatosByNombre(
                                comboBoxPokemones.getValue()).get()
                        .getMovimientos()
                        .stream()
                        .map(MovesItem::getMove)
                        .map(Move::getName)
                        .collect(Collectors.toList()));

        Image image = new Image(serviciosPokemonImpl.getDatosByNombre(
                comboBoxPokemones.getValue()).get().getSprites().getFrontDefault());
        imageView.setImage(image);
    }


    @FXML
    private void cargarDatos(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewMovimientos.getSelectionModel().getSelectedItem() != null) {
            var tarea = new Task<Either<String, Movimiento>>() {
                @Override
                protected Either<String, Movimiento> call() throws Exception {
                    return serviciosPokemonImpl.getDatosMovimiento(listViewMovimientos.getSelectionModel()
                            .getSelectedItem());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                listViewDatosMovimiento.getItems().clear();
                Try.of(() -> tarea.get()
                                .peek(movesItems -> {
                                    listViewDatosMovimiento.getItems().addAll(
                                            serviciosPokemonImpl.getDatosMovimiento(listViewMovimientos.getSelectionModel()
                                                    .getSelectedItem()).get().toString());
                                    labelMovimiento.setText(listViewMovimientos.getSelectionModel().getSelectedItem());
                                    labelDefinicion.setText(serviciosPokemonImpl.getDatosMovimiento(
                                                    listViewMovimientos.getSelectionModel().getSelectedItem()).get().getFlavorTextEntries()
                                            .stream().filter(flavorTextEntriesItem -> flavorTextEntriesItem.getLanguage().getName().equals("es"))
                                            .filter(flavorTextEntriesItem -> flavorTextEntriesItem.getVersionGroup().getName().equals("x-y"))
                                            .map(FlavorTextEntriesItem::getFlavorText).collect(Collectors.joining()));
                                })
                                .peekLeft(s -> {
                                    a.setContentText(s);
                                    a.showAndWait();
                                }))
                        .onFailure(throwable -> {
                            a.setContentText(throwable.getMessage());
                            a.showAndWait();
                        });
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                a.setContentText(workerStateEvent.getSource().getException().getMessage());
                a.showAndWait();
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        }
    }
}
