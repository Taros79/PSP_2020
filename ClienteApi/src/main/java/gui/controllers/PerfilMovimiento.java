package gui.controllers;

import dao.modelo.Move;
import gui.utils.Constantes;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import servicios.serviciosImplementacion.ServiciosMoveImpl;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PerfilMovimiento implements Initializable {


    @FXML
    private TextField textFieldNombreAdd;
    @FXML
    private TextArea textAreaDescripcionAdd;
    @FXML
    private ComboBox<Move> comboBoxMovimientos;
    @FXML
    private TextField textFieldDatos;
    @FXML
    private TextArea textAreaDescripcion;
    @FXML
    private PantallaPrincipal pantallaPrincipal;
    private Alert a;

    private ServiciosMoveImpl serviciosMoveImpl;

    @Inject
    public PerfilMovimiento(ServiciosMoveImpl serviciosMoveImpl) {
        this.serviciosMoveImpl = serviciosMoveImpl;
    }

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
        if (serviciosMoveImpl.getAllMove().isRight()) {
            comboBoxMovimientos.getItems().clear();
            comboBoxMovimientos.getItems().addAll(serviciosMoveImpl.getAllMove()
                    .get());
        } else {
            a.setContentText(serviciosMoveImpl.getAllMove().getLeft());
            a.showAndWait();
        }
    }

    @FXML
    private void onActComboMovimientos() {
        var move = comboBoxMovimientos.getSelectionModel().getSelectedItem();
        var tarea = new Task<Either<String, List<Move>>>() {
            @Override
            protected Either<String, List<Move>> call() {
                return serviciosMoveImpl.getAllMove();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            textAreaDescripcion.clear();
            Try.of(() -> tarea.get()
                            .peek(moves -> {
                                textAreaDescripcion.setText(move.getDescripcion());
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
    private void onActbuscarMovimiento() {
        String move = textFieldDatos.getText();
        var tarea = new Task<Either<String, Move>>() {
            @Override
            protected Either<String, Move> call() {
                return serviciosMoveImpl.getMove(move);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            textAreaDescripcion.clear();
            Try.of(() -> tarea.get()
                            .peek(moves -> {
                                textAreaDescripcion.setText(moves.getDescripcion());
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
    private void onActAdd() {
        Move m = new Move(textFieldNombreAdd.getText(), textAreaDescripcionAdd.getText());
        var tarea = new Task<Either<String, Move>>() {
            @Override
            protected Either<String, Move> call() {
                return serviciosMoveImpl.addMove(m);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get()
                            .peek(moves -> {
                                textAreaDescripcionAdd.clear();
                                textFieldNombreAdd.clear();
                                comboBoxMovimientos.getItems().clear();
                                comboBoxMovimientos.getItems().addAll(serviciosMoveImpl.getAllMove()
                                        .get());
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
    private void onActBorrar() {
        if (!comboBoxMovimientos.getSelectionModel().isEmpty()) {
            String id = comboBoxMovimientos.getSelectionModel().getSelectedItem().getId();
            var task = new Task<Either<String, Move>>() {
                @Override
                protected Either<String, Move> call() {
                    return serviciosMoveImpl.deleteMove(id);
                }
            };
            task.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> task.get()
                                .peek(move -> {
                                    comboBoxMovimientos.getItems().clear();
                                    comboBoxMovimientos.getItems().addAll(serviciosMoveImpl.getAllMove().get());
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
            task.setOnFailed(workerStateEvent -> {
                a.setContentText(workerStateEvent.getSource().getException().getMessage());
                a.showAndWait();
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(task).start();
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText("Constantes.ELIGE_EL_ESTUCHE");
            a.showAndWait();
        }
    }

    @FXML
    private void onActActualizar() {
        Move nuevoM = new Move(comboBoxMovimientos.getSelectionModel().getSelectedItem().getId()
                , textFieldNombreAdd.getText(), textAreaDescripcionAdd.getText());
        var tarea = new Task<Either<String, Move>>() {
            @Override
            protected Either<String, Move> call() {
                return serviciosMoveImpl.actualizarMove(nuevoM);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get()
                            .peek(moves -> {
                                textAreaDescripcionAdd.clear();
                                textFieldNombreAdd.clear();
                                textAreaDescripcion.clear();
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


}
