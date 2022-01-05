package org.example.ModuloCliente.gui.controllers;

import javax.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.example.Common.modelo.Partido;
import org.example.ModuloCliente.dao.DaoPartidos;
import org.example.ModuloCliente.dao.DaoUsuario;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultadoPartidos implements Initializable {

    @FXML
    private ListView<Partido> listViewPartidos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    private DaoPartidos daoPartidos;

    @Inject
    public ResultadoPartidos(DaoPartidos daoPartidos) {
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

            }
        }
    }

    @FXML
    private void buscarPorEquipo(ActionEvent actionEvent) {
        listViewPartidos.getItems().addAll(daoPartidos.getAllPartidos().get());
    }

    @FXML
    private void borrarAutor(ActionEvent actionEvent) {
    }
}
