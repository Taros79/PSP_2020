package gui.controllers;

import dao.modelo.Move;
import dao.modelo.Pokemon;
import gui.utils.Constantes;
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
import java.util.ResourceBundle;

public class AddPokemon implements Initializable {

    @FXML
    private ComboBox<String> comboBoxImagenes;
    @FXML
    private ListView<Move> listViewMovimientos;
    @FXML
    private ListView<Pokemon> listViewPokemon;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Move> comboBoxMovimientos;
    @FXML
    private PantallaPrincipal pantallaPrincipal;
    private Alert a;

    private ServiciosMoveImpl serviciosMoveImpl;
    private ServiciosPokemonImpl serviciosPokemonImpl;

    @Inject
    public AddPokemon(ServiciosMoveImpl serviciosMoveImpl, ServiciosPokemonImpl serviciosPokemonImpl) {
        this.serviciosMoveImpl = serviciosMoveImpl;
        this.serviciosPokemonImpl = serviciosPokemonImpl;
    }

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
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
        comboBoxImagenes.getItems().addAll("http://assets.stickpng.com/images/5a8303fdabc3d121aba71231.png", "https://www.pinclipart.com/picdir/middle/333-3332784_perro-png-personajes-de-color-amarillo-clipart.png",
                "https://img2.freepng.es/20180714/uy/kisspng-cartoon-network-drawing-character-dexter-clipart-5b4aaebd69ab67.4772045215316210534328.jpg", "https://i.pinimg.com/originals/82/a6/60/82a660275d9ec11ea48e77da34a46f65.png",
                "https://w7.pngwing.com/pngs/844/774/png-transparent-joker-evil-clown-clown-holding-a-dagger-vertebrate-fictional-character-funny.png", "http://img3.wikia.nocookie.net/__cb20110112163657/littlebigplanet/es/images/9/98/Personaje.png",
                "https://png.pngtree.com/png-clipart/20201204/ourmid/pngtree-banana-character-funny-fruit-glasses-kawaii-png-image_2504940.jpg", "https://www.clipartmax.com/png/middle/135-1358377_trolls-cooper-los-trolls-personajes-png.png",
                "https://img.pixers.pics/pho_wat(s3:700/FO/18/38/84/48/9/700_FO183884489_56351a859cb90210cd4a02ced394f592,700,700,cms:2018/10/5bd1b6b8d04b8_220x50-watermark.png,over,480,650,jpg)/cortinas-opacas-acuarela-ilustracion-personaje-de-dibujos-animados-gracioso-lavado-gris-gordo-gato.jpg.jpg", "https://toppng.com/uploads/preview/barney-personajes-barney-barney-png-transparent-11562926736e638pxyl1g.png",
                "https://upload.wikimedia.org/wikipedia/commons/b/be/Dise%C3%B1o_personaje.png", "https://cdn.goconqr.com/uploads/media/image/15797344/desktop_a2ba667f-b742-4b96-8fbb-f4f20f7b7f05.jpg");
    }

    @FXML
    private void onActAdd() {
        Pokemon p = new Pokemon(
                textFieldNombre.getText(), comboBoxImagenes.getSelectionModel().getSelectedItem(),
                datePicker.getValue().atStartOfDay(), new ArrayList<>(listViewMovimientos.getItems()));
        var tarea = new Task<Either<String, Pokemon>>() {
            @Override
            protected Either<String, Pokemon> call() {
                return serviciosPokemonImpl.addPokemon(p);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            textFieldNombre.clear();
            comboBoxImagenes.getSelectionModel().clearSelection();
            listViewMovimientos.getItems().clear();
            Try.of(() -> tarea.get()
                            .peek(pokemon -> {
                                listViewPokemon.getItems().clear();
                                listViewPokemon.getItems().addAll(serviciosPokemonImpl.getAllPokemon().get());
                                a.setContentText(Constantes.AGREGADO_CON_EXITO);
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
        listViewMovimientos.getItems().add(comboBoxMovimientos.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onActActualizar() {
        Pokemon nuevoP = new Pokemon(listViewPokemon.getSelectionModel().getSelectedItem().getId()
                , textFieldNombre.getText(), comboBoxImagenes.getSelectionModel().getSelectedItem()
                , datePicker.getValue().atStartOfDay(), listViewMovimientos.getItems());

        var tarea = new Task<Either<String, Pokemon>>() {
            @Override
            protected Either<String, Pokemon> call() {
                return serviciosPokemonImpl.actualizarPokemon(nuevoP);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get()
                            .peek(pokemon -> {
                                actualizar();
                                comboBoxMovimientos.getItems().clear();
                                comboBoxMovimientos.getItems().addAll(
                                        serviciosMoveImpl.getAllMove().get());
                                a.setContentText(Constantes.AGREGADO_CON_EXITO);
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

    public void actualizar() {
        comboBoxImagenes.getSelectionModel().clearSelection();
        comboBoxMovimientos.getItems().clear();
        comboBoxMovimientos.getItems().addAll(serviciosMoveImpl.getAllMove()
                .get());
        listViewPokemon.getItems().clear();
        listViewPokemon.getItems().addAll(serviciosPokemonImpl.getAllPokemon().get());
        listViewMovimientos.getItems().clear();
        textFieldNombre.clear();
    }


}
