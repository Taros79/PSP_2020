package controllers;

import com.github.javafaker.Faker;
import dao.modelo.Persona;
import function.Actualizar;
import function.MouseEventFunction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.converter.IntegerStringConverter;
import servicios.persona.ServicioAddPersona;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class CrearUsuarios implements Initializable {

    @FXML
    private RadioButton rbSi;
    @FXML
    private RadioButton rbNo;
    @FXML
    private RadioButton rbDepende;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEdad;
    @FXML
    private ListView<Persona> listViewUsuarios;

    @FXML
    private Principal_1 pantallaPrincipal;
    private Alert a;
    ServicioAddPersona sap = new ServicioAddPersona();

    public ListView<Persona> listaPersonas() {
        return listViewUsuarios;
    }

    public void setBorderPane(Principal_1 borderPane) {
        this.pantallaPrincipal = borderPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a =  new Alert(Alert.AlertType.INFORMATION, "Content here", ButtonType.OK);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        txtEdad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtEdad.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void rellenarFaker() {
        Faker f = new Faker();
        txtNombre.setText(f.artist().name());
        txtEdad.setText(String.valueOf(f.number().numberBetween(18, 99)));
        if (!rbSi.isSelected() && !rbNo.isSelected()) {
            rbSi.fire();
        } else {
            rbNo.fire();
            rbSi.fire();
        }
        if (rbDepende.isSelected()) {
            rbDepende.fire();
        }
    }


    @FXML
    private void addUsuario() {
        boolean mujer = rbSi.isSelected();
        if (rbDepende.isSelected()) {
            a.setContentText("La opción para personas especiales aun no esta diponible, por favor seleccione otra casilla");
            a.showAndWait();
        } else if (rbSi.isSelected() && rbNo.isSelected()) {
            a.setContentText("Opción de sexo múltiple en desarrollo, por favor elija un único tipo se sexo");
            a.showAndWait();
        } else if (!txtNombre.getText().isEmpty() && !txtEdad.getText().isEmpty() &&
                (rbSi.isSelected() || rbNo.isSelected())) {
            try {
                if (!sap.addPersona(new Persona(txtNombre.getText(), Integer.parseInt(txtEdad.getText()),
                        mujer, LocalDate.now()))) {
                    a.setContentText("Error en la base de datos");
                    a.showAndWait();
                }
                Actualizar.actualizarLView(listViewUsuarios);
            } catch (NumberFormatException e) {
                a.setContentText("Edad no es numero");
                a.showAndWait();
            }
        } else {
            a.setContentText("Algun campo esta vacio");
            a.showAndWait();
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        MouseEventFunction.mouseClick(mouseEvent, listViewUsuarios, txtNombre, txtEdad, rbSi, rbNo, rbDepende);
    }
}
