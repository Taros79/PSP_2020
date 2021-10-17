package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class PantallaPrincipal implements Initializable {

    @FXML
    private BorderPane pantallaPrincipal;

    private AnchorPane pantallaPerfilPokemon;
    private PerfilPokemon perfilPokemonController;

    private AnchorPane pantallaMercadoItems;
    private MercadoItems mercadoItemsController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void perfilPokemon() {
        FXMLLoader loaderMenu = new FXMLLoader(
                getClass().getResource("/fxml/perfilPokemon.fxml"));
        try {
            if (pantallaPerfilPokemon == null) {
                pantallaPerfilPokemon = loaderMenu.load();
                perfilPokemonController = loaderMenu.getController();
                perfilPokemonController.setBorderPane(this);
            }
            pantallaPrincipal.setCenter(pantallaPerfilPokemon);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @FXML
    private void mercadoItems(ActionEvent actionEvent) {
        FXMLLoader loaderMenu = new FXMLLoader(
                getClass().getResource("/fxml/mercadoItems.fxml"));
        try {
            if (pantallaMercadoItems == null) {
                pantallaMercadoItems = loaderMenu.load();
                mercadoItemsController = loaderMenu.getController();
                mercadoItemsController.setBorderPane(this);
            }
            pantallaPrincipal.setCenter(pantallaMercadoItems);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
