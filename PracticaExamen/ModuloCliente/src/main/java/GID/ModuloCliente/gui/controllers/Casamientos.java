package GID.ModuloCliente.gui.controllers;

import GID.Commons.EE.utils.ApiRespuesta;
import GID.Commons.dao.modelo.Persona;
import GID.ModuloCliente.servicios.serviciosImplementacion.ServiciosPersonaImpl;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Casamientos implements Initializable {
    @FXML
    private ListView<Persona> listViewHombres;
    @FXML
    private ListView<Persona> listViewMujeres;

    private PantallaPrincipal pantallaPrincipal;
    private Alert a;
    private ServiciosPersonaImpl serviciosPersona;

    @Inject
    public Casamientos(ServiciosPersonaImpl serviciosPersona) {
        this.serviciosPersona = serviciosPersona;

    }

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    @FXML
    private void realizarCasamiento() {
        if (!listViewHombres.getSelectionModel().isEmpty() && !listViewMujeres.getSelectionModel().isEmpty()) {
            String h = listViewHombres.getSelectionModel().getSelectedItem().getId();
            String m = listViewMujeres.getSelectionModel().getSelectedItem().getId();

            var task = new Task<ApiRespuesta>() {
                @Override
                protected ApiRespuesta call() throws Exception {
                    return serviciosPersona.casamientoPareja(h, m);
                }
            };
            task.setOnSucceeded(workerStateEvent -> {
                ApiRespuesta result = task.getValue();
                if (result != null) {
                    listViewMujeres.getItems().remove(listViewMujeres.getSelectionModel().getSelectedItem());
                    listViewHombres.getItems().remove(listViewHombres.getSelectionModel().getSelectedItem());

                    a.setContentText(result.getMessage());
                    a.showAndWait();
                }
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            task.setOnFailed(workerStateEvent -> {
                a.setContentText(workerStateEvent.getSource().getException().getMessage());
                a.showAndWait();
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
            new Thread(task).start();
        } else {
            a.setContentText("Elige los listView de las personas a casar");
            a.showAndWait();
        }
    }

    public void actualizar() {
        var task = new Task<Either<String, List<Persona>>>() {
            @Override
            protected Either<String, List<Persona>> call() throws Exception {
                return serviciosPersona.getAllPersona();
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            listViewHombres.getItems().clear();
            Try.of(() -> task.get().peek(persona -> listViewHombres.getItems()
                                    .addAll(persona.stream()
                                            .filter(persona1 -> persona1.getSexo().equals("H"))
                                            .filter(persona1 -> persona1.getEstadoCivil().equals("Solter@"))
                                            .collect(Collectors.toList())))
                            .peekLeft(s -> {
                                a.setContentText(String.valueOf(s));
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


        var task2 = new Task<Either<String, List<Persona>>>() {
            @Override
            protected Either<String, List<Persona>> call() throws Exception {
                return serviciosPersona.getAllPersona();
            }
        };
        task2.setOnSucceeded(workerStateEvent -> {
            listViewMujeres.getItems().clear();
            Try.of(() -> task2.get().peek(persona -> listViewMujeres.getItems()
                                    .addAll(persona.stream()
                                            .filter(persona1 -> persona1.getSexo().equals("M"))
                                            .filter(persona1 -> persona1.getEstadoCivil().equals("Solter@"))
                                            .collect(Collectors.toList())))
                            .peekLeft(s -> {
                                a.setContentText(String.valueOf(s));
                                a.showAndWait();
                            }))
                    .onFailure(throwable -> {
                        a.setContentText(throwable.getMessage());
                        a.showAndWait();
                    });
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        task2.setOnFailed(workerStateEvent -> {
            a.setContentText(workerStateEvent.getSource().getException().getMessage());
            a.showAndWait();
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        new Thread(task2).start();
        this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }
}
