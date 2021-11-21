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

    private final FXMLLoader fxmlLoaderPerfilMovimiento;
    private AnchorPane pantallaPerfilMovimiento;
    private PerfilMovimiento perfilMovimientoController;

    private final FXMLLoader fxmlLoaderAddPokemon;
    private AnchorPane pantallaAddPokemon;
    private AddPokemon addPokemonController;

    @Inject
    public PantallaPrincipal(FXMLLoader fxmlLoaderPerfilPokemon, FXMLLoader fxmlLoaderPerfilMovimiento, FXMLLoader fxmlLoaderAddPokemon) {
        this.fxmlLoaderPerfilPokemon = fxmlLoaderPerfilPokemon;
        this.fxmlLoaderPerfilMovimiento = fxmlLoaderPerfilMovimiento;
        this.fxmlLoaderAddPokemon = fxmlLoaderAddPokemon;
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
        perfilPokemonController.actualizar();
        pantallaPrincipal.setCenter(pantallaPerfilPokemon);
    }

    @FXML
    private void perfilMovimiento() {
        if (pantallaPerfilMovimiento == null) {
            try {
                pantallaPerfilMovimiento = fxmlLoaderPerfilMovimiento.load(getClass().getResourceAsStream("/fxml/perfilMovimiento.fxml"));
                perfilMovimientoController = fxmlLoaderPerfilMovimiento.getController();
                perfilMovimientoController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        pantallaPrincipal.setCenter(pantallaPerfilMovimiento);
    }

    @FXML
    private void addPokemon() {
        if (pantallaAddPokemon == null) {
            try {
                pantallaAddPokemon = fxmlLoaderAddPokemon.load(getClass().getResourceAsStream("/fxml/addPokemon.fxml"));
                addPokemonController = fxmlLoaderAddPokemon.getController();
                addPokemonController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        addPokemonController.actualizar();
        pantallaPrincipal.setCenter(pantallaAddPokemon);
    }
}
