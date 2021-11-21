package gui.controllers;

import dao.modelo.Move;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import servicios.ServiciosMove;
import servicios.serviciosImplementacion.ServiciosMoveImpl;
import servicios.serviciosImplementacion.ServiciosPokemonImpl;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PerfilMovimiento implements Initializable {


    @FXML
    private ComboBox<String> comboBoxMovimientos;
    @FXML
    private TextField textFieldDatos;
    @FXML
    private TextArea textAreaDescripcion;
    @FXML
    private PantallaPrincipal pantallaPrincipal;
    private Alert a;
    ServiciosMoveImpl serviciosMoveImpl;

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Inject
    public PerfilMovimiento(ServiciosMoveImpl serviciosMoveImpl) {
        this.serviciosMoveImpl = serviciosMoveImpl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
        if (serviciosMoveImpl.getAllMove().isRight()) {
            comboBoxMovimientos.getItems().addAll(serviciosMoveImpl.getAllMove()
                    .get()
                    .stream().map(move -> move.getName())
                    .collect(Collectors.toList()));
        } else {
            a.setContentText(serviciosMoveImpl.getAllMove().getLeft());
            a.showAndWait();
        }
    }

    @FXML
    public void onActComboMovimientos(ActionEvent actionEvent) {
    }

    @FXML
    public void onActbuscarMovimiento(ActionEvent actionEvent) {
    }
}
