package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    private Alert alert;

    @Getter
    @Setter
    private String nameUsuario;

    private AnchorPane pantalla_login;
    private ApiFootballController controller_login;


    @FXML
    private BorderPane pantallaPrincipal;

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @FXML
    private MenuBar menuTienda;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        cargarLogin();
        menuTienda.setVisible(false);
    }

    @SneakyThrows
    public void cargarLogin() {
        if (pantalla_login == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/apifootball.fxml"));
            pantalla_login = fxmlLoader.load();
            controller_login = fxmlLoader.getController();
            controller_login.setPrincipalController(this);
        }
        pantallaPrincipal.setCenter(pantalla_login);
    }


}
