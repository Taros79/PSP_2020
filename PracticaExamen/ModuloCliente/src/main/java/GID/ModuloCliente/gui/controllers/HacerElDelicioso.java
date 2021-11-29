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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HacerElDelicioso implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> comboBoxSexo;
    @FXML
    private DatePicker datePikerNacimiento;
    @FXML
    private TextField textFieldLugarNacimiento;
    @FXML
    private ListView<Persona> listViewPadres;
    @FXML
    private ListView<Persona> listViewHijos;

    private PantallaPrincipal pantallaPrincipal;
    private Alert a;
    private ServiciosPersonaImpl serviciosPersona;

    @Inject
    public HacerElDelicioso(ServiciosPersonaImpl serviciosPersona) {
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
    private void procrear() {
        if (!listViewPadres.getSelectionModel().isEmpty() && !txtNombre.getText().isEmpty()
                && !textFieldLugarNacimiento.getText().isEmpty() && !comboBoxSexo.getSelectionModel().isEmpty()
                && !datePikerNacimiento.getValue().toString().isEmpty()) {

            Persona ultimoLista = serviciosPersona.getAllPersona().get()
                    .stream().reduce((a, b) -> b).orElse(null);

            int num = Integer.parseInt(ultimoLista.getId()) + 1;

            Persona p = new Persona(String.valueOf(num), txtNombre.getText(), "Solter@"
                    , comboBoxSexo.getSelectionModel().getSelectedItem()
                    , textFieldLugarNacimiento.getText()
                    , "",datePikerNacimiento.getValue().atStartOfDay()
                    , new ArrayList<>());

            var tarea = new Task<Either<String, Persona>>() {
                @Override
                protected Either<String, Persona> call() throws Exception {
                    return serviciosPersona.addPersona(p);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(persona -> listViewHijos.getItems().addAll(persona)
                                )
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

            var task = new Task<ApiRespuesta>() {
                @Override
                protected ApiRespuesta call() throws Exception {
                    return serviciosPersona.procrear(p, listViewPadres.getSelectionModel().getSelectedItem().getId());
                }
            };
            task.setOnSucceeded(workerStateEvent -> {
                ApiRespuesta result = task.getValue();
                if (result != null) {
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
            a.setContentText("Rellena los campos");
            a.showAndWait();
        }
    }

    @FXML
    private void cargarHijos(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            var task = new Task<Either<String, List<Persona>>>() {
                @Override
                protected Either<String, List<Persona>> call() throws Exception {
                    return serviciosPersona.getAllPersona();
                }
            };
            task.setOnSucceeded(workerStateEvent -> {
                listViewHijos.getItems().clear();
                Try.of(() -> task.get().peek(persona -> listViewHijos.getItems().addAll(persona.stream()
                                                .filter(persona1 -> persona1.getId().equals(
                                                        listViewPadres.getSelectionModel().getSelectedItem().getId()))
                                                .flatMap(persona1 -> persona1.getHijos().stream())
                                                .collect(Collectors.toList())))
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
            listViewPadres.getItems().clear();
            Try.of(() -> task.get().peek(persona -> listViewPadres.getItems()
                                    .addAll(persona.stream()
                                            .filter(persona1 -> persona1.getEstadoCivil().equals("Casad@"))
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
    }
}
