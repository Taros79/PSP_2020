package org.example.ModuloCliente.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.example.Common.modelo.Jornada;
import org.example.ModuloCliente.servicios.ServiciosEquipo;
import org.example.ModuloCliente.servicios.ServiciosJornada;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministracionJornadas implements Initializable {
    @FXML
    private DatePicker datePicker;
    @FXML
    private ListView<Jornada> listViewJornadas;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private ServiciosJornada serviciosJornada;

    @Inject
    public AdministracionJornadas(ServiciosJornada serviciosJornada) {
        this.serviciosJornada = serviciosJornada;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    public void actualizar() {
        listViewJornadas.getItems().clear();
        listViewJornadas.getItems().addAll(serviciosJornada.getAllJornadas().get());
    }

    @FXML
    private void AddJornada(ActionEvent actionEvent) {

    }

    @FXML
    private void borrarJornada(ActionEvent actionEvent) {

    }

    @FXML
    private void actualizarJornada(ActionEvent actionEvent) {

    }
}
