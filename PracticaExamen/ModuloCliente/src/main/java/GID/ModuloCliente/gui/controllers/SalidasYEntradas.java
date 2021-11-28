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

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SalidasYEntradas implements Initializable {

    @FXML
    private ListView<Persona> listViewPersonas;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldLugarNacimiento;
    @FXML
    private ComboBox<String> comboBoxSexo;
    @FXML
    private DatePicker datePikerNacimiento;

    private PantallaPrincipal pantallaPrincipal;
    private Alert a;

    private ServiciosPersonaImpl serviciosPersona;

    @Inject
    public SalidasYEntradas(ServiciosPersonaImpl serviciosPersona) {
        this.serviciosPersona = serviciosPersona;

    }

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }


    @FXML
    private void botonAñadir() {
        if (!textFieldNombre.getText().isEmpty() && !textFieldLugarNacimiento.getText().isEmpty()
                && !comboBoxSexo.getSelectionModel().isEmpty() && !datePikerNacimiento.getValue().toString().isEmpty()) {

            Persona p = new Persona(textFieldNombre.getText(), "Solter@", comboBoxSexo.getValue(),
                    datePikerNacimiento.getValue().atStartOfDay(), textFieldLugarNacimiento.getText(), "", new ArrayList<>());

            var tarea = new Task<Either<String, Persona>>() {
                @Override
                protected Either<String, Persona> call() throws Exception {
                    return serviciosPersona.addPersona(p);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(persona -> listViewPersonas.getItems().addAll(persona))
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

    @FXML
    private void botonBorrar() {
        if (!listViewPersonas.getSelectionModel().isEmpty()) {
            String id = listViewPersonas.getSelectionModel().getSelectedItem().getId();

            var task = new Task<ApiRespuesta>() {
                @Override
                protected ApiRespuesta call() throws Exception {
                    return serviciosPersona.deletePersona(id);
                }
            };
            task.setOnSucceeded(workerStateEvent -> {
                ApiRespuesta result = task.getValue();
                if (result != null) {
                    a.setContentText(result.getMessage());
                    a.showAndWait();
                    actualizar();
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
            a.setContentText("Elige la persona");
            a.showAndWait();
        }
    }

    public void actualizar() {
        listViewPersonas.getItems().clear();
        listViewPersonas.getItems().addAll(serviciosPersona.getAllPersona().get());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }
}
