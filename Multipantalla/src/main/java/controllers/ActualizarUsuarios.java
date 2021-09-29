package controllers;

import dao.modelo.Persona;
import function.Actualizar;
import function.MouseEventFunction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import servicios.persona.ServicioGetPersona;
import servicios.persona.ServiciosUpdatePersona;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ActualizarUsuarios implements Initializable {

    @FXML
    private ListView<Persona> listViewUsuarios;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEdad;
    @FXML
    private RadioButton rbSi;
    @FXML
    private RadioButton rbNo;
    @FXML
    private RadioButton rbDepende;

    @FXML
    private Principal_1 pantallaPrincipal;
    private Alert a;
    ServiciosUpdatePersona serviciosUpdatePersona = new ServiciosUpdatePersona();
    ServicioGetPersona sgp = new ServicioGetPersona();

    public void setBorderPane(Principal_1 borderPane) {
        this.pantallaPrincipal = borderPane;
    }

    public ListView<Persona> listaPersonas() {
        return listViewUsuarios;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a =  new Alert(Alert.AlertType.INFORMATION, "Content here", ButtonType.OK);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    }

    @FXML
    private void modUsuario() {
        if(listViewUsuarios.getSelectionModel().getSelectedItem() != null) {
            if (rbDepende.isSelected()) {
                a.setContentText("Ahhh Pensaste en hacerte una cuenta y después modificar tu sexo pinche puerco." +
                        ".. Muy feo por tu parte si ya sabías que no se puede, para que lo intentas puercoo, " +
                        "acaso quieres tirarme mi humilde programa cerdo...acadecepcionado ayome");
            } else if (rbSi.isSelected() && rbNo.isSelected()) {
                a.setContentText("¿Estas seguro?");
            } else if (!txtNombre.getText().isEmpty() && !txtEdad.getText().isEmpty()) {
                Persona vieja = sgp.getPersona(listViewUsuarios.getSelectionModel().getSelectedIndex());
                Persona nueva = new Persona(txtNombre.getText(), Integer.parseInt(txtEdad.getText()),
                        rbSi.isSelected(), LocalDate.now());
                if (!serviciosUpdatePersona.updatePersona(vieja, nueva)) {
                    a.setContentText("Error en la base de datos");
                }else{
                    a.setContentText("Modificación exitosa");
                    Actualizar.actualizarLView(listViewUsuarios);
                }
            } else {
                a.setContentText("Algun campo esta vacio");
            }
        }else{
            a.setContentText("Ningun usuario seleccionado");
        }
        a.showAndWait();
    }

    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        MouseEventFunction.mouseClick(mouseEvent, listViewUsuarios, txtNombre, txtEdad, rbSi, rbNo, rbDepende);
    }
}
