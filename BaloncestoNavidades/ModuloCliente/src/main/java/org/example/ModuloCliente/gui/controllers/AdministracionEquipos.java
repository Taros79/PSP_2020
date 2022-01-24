package org.example.ModuloCliente.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.Common.modelo.Equipo;
import org.example.ModuloCliente.dao.DaoPartidos;
import org.example.ModuloCliente.servicios.ServiciosEquipo;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministracionEquipos implements Initializable {

    @FXML
    private TextField textFieldNombre;
    @FXML
    private ListView<Equipo> listViewEquipos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private ServiciosEquipo serviciosEquipo;

    @Inject
    public AdministracionEquipos(ServiciosEquipo serviciosEquipo) {
        this.serviciosEquipo = serviciosEquipo;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewEquipos.getSelectionModel().getSelectedItem() != null) {
            if (mouseEvent.getClickCount() == 1 && listViewEquipos.getSelectionModel().getSelectedItem() != null) {
                textFieldNombre.setText(listViewEquipos.getSelectionModel().getSelectedItem().getNombre());
            }
        }
    }

    public void actualizar() {
        textFieldNombre.clear();
        listViewEquipos.getItems().clear();
        listViewEquipos.getItems().addAll(serviciosEquipo.getAllEquipos().get());
    }

    @FXML
    private void AddEquipo() {
        serviciosEquipo.addEquipo(new Equipo(textFieldNombre.getText()));
        actualizar();
    }

    @FXML
    private void borrarEquipo() {
        textFieldNombre.clear();
        serviciosEquipo.deleteEquipo(listViewEquipos.getSelectionModel().getSelectedItem().getNombre());
        actualizar();
    }

    @FXML
    private void ActualizarEquipo() {
        var id = listViewEquipos.getSelectionModel().getSelectedItem().getIdEquipo();
        serviciosEquipo.updateEquipo(new Equipo(id, textFieldNombre.getText()));
        actualizar();
    }
}
