package notas.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import notas.Cliente.gui.controllers.FXMLPrincipalController;

import java.net.URL;
import java.util.ResourceBundle;

import static notas.Cliente.gui.utils.OnlyNumbers.onlyNum;


public class ConsultasPadre implements Initializable {

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }


    public void actualizarDatos() {

    }

    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {

    }

    @FXML
    private void updateEstadisticas() {

    }


}