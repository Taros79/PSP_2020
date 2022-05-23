package notas.ClienteModule.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import notas.ClienteModule.Servicios.ServiciosAlumno;
import notas.ClienteModule.Servicios.ServiciosParte;
import notas.ClienteModule.gui.ConstantesGUI;
import notas.ClienteModule.gui.controllers.FXMLPrincipalController;
import notas.CommonModule.modelo.Alumno;
import notas.CommonModule.modelo.Parte;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;


public class PonerParte implements Initializable {

    @FXML
    private ComboBox<Alumno> comboAlumnos;
    @FXML
    private TextArea textAreaParte;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosParte serviciosParte;
    private ServiciosAlumno serviciosAlumno;

    @Inject
    public PonerParte(ServiciosParte serviciosParte, ServiciosAlumno serviciosAlumno) {
        this.serviciosParte = serviciosParte;
        this.serviciosAlumno = serviciosAlumno;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }

    public void actualizarDatos() {
        textAreaParte.clear();
        serviciosAlumno.getAllAlumnos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    comboAlumnos.getItems().clear();
                                                    comboAlumnos.getItems().addAll(action);
                                                }
                                        )
                                        .peekLeft(error -> {
                                            a = new Alert(Alert.AlertType.ERROR, error);
                                            a.showAndWait();
                                        }),
                        throwable -> {
                            a = new Alert(Alert.AlertType.ERROR, ConstantesGUI.FALLO_AL_REALIZAR_LA_PETICION);
                            a.showAndWait();
                        }
                );
        pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    @FXML
    public void añadirParte() {
        if (!comboAlumnos.getSelectionModel().isEmpty()) {
            serviciosParte.addParte(new Parte(textAreaParte.getText(), comboAlumnos.getSelectionModel().getSelectedItem().getId()))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        a = new Alert(Alert.AlertType.INFORMATION, action);
                                                        a.showAndWait();
                                                    }
                                            )
                                            .peekLeft(error -> {
                                                a = new Alert(Alert.AlertType.ERROR, error);
                                                a.showAndWait();
                                            }),
                            throwable -> {
                                a = new Alert(Alert.AlertType.ERROR, ConstantesGUI.FALLO_AL_REALIZAR_LA_PETICION);
                                a.showAndWait();
                            }
                    );
            pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
            actualizarDatos();

        } else {
            a.setContentText(ConstantesGUI.SELECCIONA_EN_LA_LISTA);
            a.showAndWait();
        }
    }
}