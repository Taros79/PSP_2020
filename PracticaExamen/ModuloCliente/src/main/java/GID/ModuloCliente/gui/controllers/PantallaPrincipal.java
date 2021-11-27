package GID.ModuloCliente.gui.controllers;

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

    private final FXMLLoader fxmlLoaderAddPokemon;
    private AnchorPane pantallaAddPokemon;
    private AddPersona addPersonaController;

    @Inject
    public PantallaPrincipal(FXMLLoader fxmlLoaderAddPokemon) {
        this.fxmlLoaderAddPokemon = fxmlLoaderAddPokemon;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void addPokemon() {
        if (pantallaAddPokemon == null) {
            try {
                pantallaAddPokemon = fxmlLoaderAddPokemon.load(getClass().getResourceAsStream("/fxml/addPokemon.fxml"));
                addPersonaController = fxmlLoaderAddPokemon.getController();
               // addPersonaController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        addPersonaController.actualizar();
        pantallaPrincipal.setCenter(pantallaAddPokemon);
    }
}
