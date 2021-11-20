package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class PantallaPrincipal implements Initializable {

    @FXML
    private BorderPane pantallaPrincipal;

    private final FXMLLoader fxmlLoaderPerfilPokemon;
    private AnchorPane pantallaPerfilPokemon;
    private PerfilPokemon perfilPokemonController;

    @Inject
    public PantallaPrincipal(FXMLLoader fxmlLoaderPerfilPokemon) {
        this.fxmlLoaderPerfilPokemon = fxmlLoaderPerfilPokemon;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void perfilPokemon() {
        if (pantallaPerfilPokemon == null) {
            try {
                pantallaPerfilPokemon = fxmlLoaderPerfilPokemon.load(getClass().getResourceAsStream("/fxml/perfilPokemon.fxml"));
                perfilPokemonController = fxmlLoaderPerfilPokemon.getController();
                perfilPokemonController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        pantallaPrincipal.setCenter(pantallaPerfilPokemon);
    }
}
