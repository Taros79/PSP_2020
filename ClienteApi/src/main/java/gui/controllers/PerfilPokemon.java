package gui.controllers;

import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;
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
    private ComboBox<Pokemon> comboBoxPokemones;
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
            Try.of(() -> tarea.get()
                            .peek(moves -> {
                                listViewMovimientos.getItems().addAll(moves.stream()
                                        .map(Move::getName)
                                        .collect(Collectors.toList()));
                                imageView.setImage(new Image(serviciosPokemonImpl
                                        .getDatosByNombre(pokemon).get()
                                        .getImage()));
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
        var pokemon = comboBoxPokemones.getSelectionModel().getSelectedItem().getId();
        var tarea = new Task<Either<String, List<Move>>>() {
            @Override
            protected Either<String, List<Move>> call() {
                return serviciosPokemonImpl.getMovimientosPorId(pokemon);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            listViewMovimientos.getItems().clear();
            Try.of(() -> tarea.get()
                            .peek(moves -> {
                                listViewMovimientos.getItems().addAll(moves.stream()
                                        .map(Move::getName)
                                        .collect(Collectors.toList()));
                                imageView.setImage(new Image(serviciosPokemonImpl
                                        .getDatosByNombre(pokemon).get()
                                        .getImage()));
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
                                imageView.getImage().cancel();
                                a.setContentText("Pokemon borrado con exito");
                                a.showAndWait();
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
    private void cargarDatos(MouseEvent mouseEvent) {
       /* if (mouseEvent.getClickCount() == 1 && listViewMovimientos.getSelectionModel().getSelectedItem() != null) {
            var tarea = new Task<Either<String, MovimientoPrp>>() {
                @Override
                protected Either<String, MovimientoPrp> call() {
                    return serviciosPokemonImpl.getDatosMovimiento(listViewMovimientos.getSelectionModel()
                            .getSelectedItem());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                listViewDatosMovimiento.getItems().clear();
                Try.of(() -> tarea.get()
                                .peek(movesItems -> {
                                    listViewDatosMovimiento.getItems().addAll(movesItems.toString());
                                    labelMovimiento.setText(listViewMovimientos.getSelectionModel().getSelectedItem());
                                    labelDefinicion.setText(movesItems.getFlavorTextEntries()
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
        }*/
    }


}
