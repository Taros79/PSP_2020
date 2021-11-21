package gui.controllers;

import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import servicios.serviciosImplementacion.ServiciosMoveImpl;
import servicios.serviciosImplementacion.ServiciosPokemonImpl;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddPokemon implements Initializable {

    @FXML
    private ListView<Move> listViewMovimientos;
    @FXML
    private ListView<Pokemon> listViewPokemon;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldImagen;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Move> comboBoxMovimientos;
    @FXML
    private PantallaPrincipal pantallaPrincipal;
    private Alert a;
    ServiciosMoveImpl serviciosMoveImpl;
    ServiciosPokemonImpl serviciosPokemonImpl;

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Inject
    public AddPokemon(ServiciosMoveImpl serviciosMoveImpl, ServiciosPokemonImpl serviciosPokemonImpl) {
        this.serviciosMoveImpl = serviciosMoveImpl;
        this.serviciosPokemonImpl = serviciosPokemonImpl;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
        if (serviciosMoveImpl.getAllMove().isRight() && serviciosPokemonImpl.getAllPokemon().isRight()) {
            comboBoxMovimientos.getItems().clear();
            comboBoxMovimientos.getItems().addAll(serviciosMoveImpl.getAllMove()
                    .get());
            listViewPokemon.getItems().clear();
            listViewPokemon.getItems().addAll(serviciosPokemonImpl.getAllPokemon().get());
        } else {
            if (serviciosMoveImpl.getAllMove().isRight()) {
                a.setContentText(serviciosPokemonImpl.getAllPokemon().getLeft());
            } else {
                a.setContentText(serviciosMoveImpl.getAllMove().getLeft());
            }
            a.showAndWait();
        }
    }

    @FXML
    private void onActAdd() {
        Pokemon p = new Pokemon(
                textFieldNombre.getText(), textFieldImagen.getText(),
                datePicker.getValue().atStartOfDay(), new ArrayList<>(listViewMovimientos.getItems()));
        var tarea = new Task<Either<String, Pokemon>>() {
            @Override
            protected Either<String, Pokemon> call() {
                return serviciosPokemonImpl.addPokemon(p);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            textFieldNombre.clear();
            textFieldImagen.clear();
            listViewMovimientos.getItems().clear();
            Try.of(() -> tarea.get()
                            .peek(pokemon -> {
                                listViewPokemon.getItems().clear();
                                listViewPokemon.getItems().addAll(serviciosPokemonImpl.getAllPokemon().get());
                                a.setContentText("Pokemon añadido");
                                a.showAndWait();
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

    @FXML
    private void onActCombo() {
        listViewMovimientos.getItems().add(comboBoxMovimientos.getValue());
        comboBoxMovimientos.getSelectionModel().clearSelection();
    }

    public void actualizar(){
        comboBoxMovimientos.getItems().clear();
        comboBoxMovimientos.getItems().addAll(serviciosMoveImpl.getAllMove()
                .get());
    }
}
