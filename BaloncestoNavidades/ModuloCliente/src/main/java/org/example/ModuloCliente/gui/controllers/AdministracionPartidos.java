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

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministracionPartidos implements Initializable {

    @FXML
    private TextField textFieldNombre;
    @FXML
    private ListView<Equipo> listViewPartidos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private DaoPartidos daoPartidos;

    @Inject
    public AdministracionPartidos(DaoPartidos daoPartidos) {
        this.daoPartidos = daoPartidos;
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
        if (mouseEvent.getClickCount() == 1 && listViewPartidos.getSelectionModel().getSelectedItem() != null) {
            if (mouseEvent.getClickCount() == 1 && listViewPartidos.getSelectionModel().getSelectedItem() != null) {
                textFieldNombre.setText(listViewPartidos.getSelectionModel().getSelectedItem().getNombre());
            }
        }
    }

    @FXML
    private void buscarPorEquipo(ActionEvent actionEvent) {
    }

    @FXML
    private void borrarAutor(ActionEvent actionEvent) {
        textFieldNombre.clear();
      //  listViewPartidos.getItems().clear();
       // listViewPartidos.getItems().addAll(daoPartidos.getAllEquipos().get());
    }

    public void actualizar() {
        textFieldNombre.clear();
       // listViewPartidos.getItems().clear();
       // listViewPartidos.getItems().addAll(daoPartidos.getAllEquipos().get());
    }

    @FXML
    private void AddEquipo(ActionEvent actionEvent) {
       // daoPartidos.addEquipo(new Equipo("ClusNovilllin"));
    }
}
