package gui.controllers;
import dao.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import servicios.ServiciosUsuario;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministracionPartidos implements Initializable {

    @FXML
    private ListView<Usuario> listView1;
    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosUsuario serviciosUsuario;

    @Inject
    public AdministracionPartidos(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }


    public void get(ActionEvent actionEvent) {
       listView1.getItems().addAll(serviciosUsuario.getUsuarios().get());
    }
}
