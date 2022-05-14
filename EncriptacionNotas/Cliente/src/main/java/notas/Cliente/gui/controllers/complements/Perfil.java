package notas.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import notas.Cliente.gui.ConstantesGUI;
import notas.Cliente.gui.controllers.FXMLPrincipalController;
import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import static notas.Cliente.gui.utils.OnlyNumbers.onlyNum;


public class Perfil implements Initializable {

    @FXML
    private TextField tfVida;
    @FXML
    private TextField tfDestreza;
    @FXML
    private TextField tfInteligencia;
    @FXML
    private TextField tfConstitution;
    @FXML
    private TextField tfSabiduria;
    @FXML
    private TextField tfCarisma;
    @FXML
    private TextField tfAc;
    @FXML
    private TextField tfFortaleza;
    @FXML
    private TextField tfReflejos;
    @FXML
    private TextField tfVoluntad;
    @FXML
    private TextField tfFuerza;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);

        onlyNum(tfVida);
        onlyNum(tfAc);
        onlyNum(tfFortaleza);
        onlyNum(tfReflejos);
        onlyNum(tfVoluntad);
        onlyNum(tfFuerza);
        onlyNum(tfDestreza);
        onlyNum(tfConstitution);
        onlyNum(tfInteligencia);
        onlyNum(tfSabiduria);
        onlyNum(tfCarisma);
    }

    private void getEstadisticas() {

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