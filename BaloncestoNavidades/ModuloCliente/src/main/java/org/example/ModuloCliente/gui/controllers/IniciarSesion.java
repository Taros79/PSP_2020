package org.example.ModuloCliente.gui.controllers;

import javax.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.Common.modelo.Usuario;
import org.example.ModuloCliente.dao.DaoUsuario;

import java.net.URL;
import java.util.ResourceBundle;

public class IniciarSesion implements Initializable {

    @FXML
    private ListView<Usuario> listViewUsuarios;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPass;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;

    @Inject
    DaoUsuario daoUsuario;


    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    public void actualizar() {


    }

    public void añadir(ActionEvent actionEvent) {
        if(daoUsuario.getAllUsuario() != null){
            listViewUsuarios.getItems().addAll(daoUsuario.getAllUsuario().get());
        }else{
            System.out.println("no mames");
        }
    }
}