package controllers;

import dao.modelo.Persona;
import function.Actualizar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import servicios.persona.ServicioDelPersona;

import java.net.URL;
import java.util.ResourceBundle;

public class BorrarUsuarios implements Initializable {

    @FXML
    private ListView<Persona> listViewUsuarios;

    @FXML
    private Principal_1 pantallaPrincipal;
    private Alert a;
    ServicioDelPersona servicioDelPersona = new ServicioDelPersona();

    public ListView<Persona> listaPersonas() {
        return listViewUsuarios;
    }

    public void setBorderPane(Principal_1 borderPane) {
        this.pantallaPrincipal = borderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a =  new Alert(Alert.AlertType.INFORMATION, "Content here", ButtonType.OK);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    }

    @FXML
    private void delUsuario() {
        if (listViewUsuarios.getSelectionModel().getSelectedItem() != null) {
            if (!servicioDelPersona.delPersona(listViewUsuarios.getSelectionModel().getSelectedItem())) {
                a.setContentText("Error en la base de datos");
                a.showAndWait();
            }
            Actualizar.actualizarLView(listViewUsuarios);
        } else {
            a.setContentText("Ningún usuario seleccionado");
            a.showAndWait();
        }
    }

}
