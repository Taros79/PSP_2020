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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import servicios.serviciosImplementacion.ServiciosMoveImpl;
import servicios.serviciosImplementacion.ServiciosPokemonImpl;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PerfilPokemon implements Initializable {

    @FXML
    private TextArea textAreaDefinicion;
    @FXML
    private Label labelMovimiento;
    @FXML
    private TextField textFieldDatos;
    @FXML
    private ImageView imageView;
    @FXML
    private ListView<Move> listViewMovimientos;
    @FXML
    private ComboBox<Pokemon> comboBoxPokemones;
    @FXML
    private PantallaPrincipal pantallaPrincipal;
    private Alert a;

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    private ServiciosPokemonImpl serviciosPokemonImpl;
    private ServiciosMoveImpl serviciosMoveImpl;

    @Inject
    public PerfilPokemon(ServiciosPokemonImpl serviciosPokemonImpl, ServiciosMoveImpl serviciosMoveImpl) {
        this.serviciosPokemonImpl = serviciosPokemonImpl;
        this.serviciosMoveImpl = serviciosMoveImpl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
        if (serviciosPokemonImpl.getAllPokemon().isRight()) {
            comboBoxPokemones.getItems().addAll(new ArrayList<>(serviciosPokemonImpl.getAllPokemon()
                    .get()));
        } else {
            a.setContentText(serviciosPokemonImpl.getAllPokemon().getLeft());
            a.showAndWait();
        }
    }

    @FXML
    private void buscarPokemon() {
        String pokemon = textFieldDatos.getText();
        var tarea = new Task<Either<String, List<Move>>>() {
            @Override
            protected Either<String, List<Move>> call() {
                return serviciosPokemonImpl.getMovimientosPorId(pokemon);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            listViewMovimientos.getItems().clear();
            textAreaDefinicion.clear();
            Try.of(() -> tarea.get()
                            .peek(moves -> {
                                listViewMovimientos.getItems().addAll(moves);
                                imageView.setImage(new Image(serviciosPokemonImpl
                                        .getDatosByNombre(pokemon).get()
                                        .getImage()));
                            })
                            .peekLeft(s -> {
                                imageView.setImage(new Image(Constantes.WHOs_IS_TATH_POKEMONE_IMG));
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
        var pokemon = comboBoxPokemones.getSelectionModel().getSelectedItem().getId();
        var tarea = new Task<Either<String, List<Move>>>() {
            @Override
            protected Either<String, List<Move>> call() {
                return serviciosPokemonImpl.getMovimientosPorId(pokemon);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            listViewMovimientos.getItems().clear();
            textAreaDefinicion.clear();
            Try.of(() -> tarea.get()
                            .peek(moves -> {
                                listViewMovimientos.getItems().addAll(moves);
                                imageView.setImage(new Image(serviciosPokemonImpl
                                        .getDatosByNombre(pokemon).get()
                                        .getImage()));
                            })
                            .peekLeft(s -> {
                                imageView.setImage(new Image(Constantes.WHOs_IS_TATH_POKEMONE_IMG));
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
    private void borrarPokemon() {
        var pokemon = comboBoxPokemones.getSelectionModel().getSelectedItem().getId();
        var tarea = new Task<Either<String, Pokemon>>() {
            @Override
            protected Either<String, Pokemon> call() {
                return serviciosPokemonImpl.deletePokemon(pokemon);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            listViewMovimientos.getItems().clear();
            Try.of(() -> tarea.get()
                            .peek(pokemon1 -> {
                                comboBoxPokemones.getItems().clear();
                                comboBoxPokemones.getItems().addAll(
                                        new ArrayList<>(serviciosPokemonImpl.getAllPokemon()
                                                .get()));
                                imageView.setImage(new Image(Constantes.WHOs_IS_TATH_POKEMONE_IMG));
                                a.setContentText(Constantes.BORRADO_CON_EXITO);
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
    private void cargarDatos(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewMovimientos.getSelectionModel().getSelectedItem() != null) {
            var tarea = new Task<Either<String, Move>>() {
                @Override
                protected Either<String, Move> call() {
                    return serviciosMoveImpl.getDatosMovimiento(listViewMovimientos.getSelectionModel()
                            .getSelectedItem().getId());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get()
                                .peek(movesItems -> {
                                    labelMovimiento.setText(listViewMovimientos.getSelectionModel().getSelectedItem().getName());
                                    textAreaDefinicion.setText(movesItems.getDescripcion());
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

    public void actualizar() {
        comboBoxPokemones.getItems().clear();
        comboBoxPokemones.getItems().addAll(new ArrayList<>(serviciosPokemonImpl.getAllPokemon()
                .get()));
    }
}
