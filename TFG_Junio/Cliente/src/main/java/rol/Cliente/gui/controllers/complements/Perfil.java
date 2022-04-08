package rol.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import rol.Cliente.Servicios.ServiciosDote;
import rol.Cliente.Servicios.ServiciosEstadistica;
import rol.Cliente.Servicios.ServiciosHechizo;
import rol.Cliente.Servicios.ServiciosPersonaje;
import rol.Cliente.gui.ConstantesGUI;
import rol.Cliente.gui.controllers.FXMLPrincipalController;
import rol.Common.modelo.Dote;
import rol.Common.modelo.Estadistica;
import rol.Common.modelo.Hechizo;
import rol.Common.modelo.Personaje;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

import static rol.Cliente.gui.utils.OnlyNumbers.onlyNum;


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
    @FXML
    private ListView<Estadistica> listViewEstadísticas;
    @FXML
    private ListView<Personaje> listViewPersonajes;
    @FXML
    private ListView<Dote> listViewDotes;
    @FXML
    private ListView<Hechizo> listViewHechizos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosDote serviciosDote;
    private ServiciosPersonaje serviciosPersonaje;
    private ServiciosEstadistica serviciosEstadistica;
    private ServiciosHechizo serviciosHechizo;

    private int id_Estadistica = 0;


    @Inject
    public Perfil(ServiciosDote serviciosDote, ServiciosPersonaje serviciosPersonaje,
                  ServiciosEstadistica serviciosEstadistica, ServiciosHechizo serviciosHechizo) {
        this.serviciosDote = serviciosDote;
        this.serviciosPersonaje = serviciosPersonaje;
        this.serviciosEstadistica = serviciosEstadistica;
        this.serviciosHechizo = serviciosHechizo;

    }

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
        serviciosEstadistica.getEstadisticaById(listViewPersonajes.getSelectionModel().getSelectedItem().getId_Estadistica())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    id_Estadistica = action.getId();
                                                    listViewEstadísticas.getItems().clear();
                                                    listViewEstadísticas.getItems().addAll(action);
                                                    tfVida.setText(String.valueOf(action.getVida()));
                                                    tfAc.setText(String.valueOf(action.getAc()));
                                                    tfFortaleza.setText(String.valueOf(action.getFortaleza()));
                                                    tfReflejos.setText(String.valueOf(action.getReflejos()));
                                                    tfVoluntad.setText(String.valueOf(action.getVoluntad()));
                                                    tfFuerza.setText(String.valueOf(action.getFuerza()));
                                                    tfDestreza.setText(String.valueOf(action.getDestreza()));
                                                    tfConstitution.setText(String.valueOf(action.getConstitucion()));
                                                    tfInteligencia.setText(String.valueOf(action.getInteligencia()));
                                                    tfSabiduria.setText(String.valueOf(action.getSabiduria()));
                                                    tfCarisma.setText(String.valueOf(action.getCarisma()));
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

    public void actualizarDatos() {
        listViewPersonajes.getItems().clear();
        serviciosPersonaje.getPersonajesByIdUsuario(pantallaPrincipal.getUsuarioLoginPrincipal().getId())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    this.id_Estadistica = 0;
                                                    listViewEstadísticas.getItems().clear();
                                                    listViewHechizos.getItems().clear();
                                                    listViewDotes.getItems().clear();
                                                    tfVida.clear();
                                                    tfAc.clear();
                                                    tfFortaleza.clear();
                                                    tfReflejos.clear();
                                                    tfVoluntad.clear();
                                                    tfFuerza.clear();
                                                    tfDestreza.clear();
                                                    tfConstitution.clear();
                                                    tfInteligencia.clear();
                                                    tfSabiduria.clear();
                                                    tfCarisma.clear();
                                                    listViewPersonajes.getItems().addAll(action);
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
    private void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewPersonajes.getSelectionModel().getSelectedItem() != null) {
            getEstadisticas();
            serviciosDote.getDotesByIdPersonaje(listViewPersonajes.getSelectionModel().getSelectedItem().getId())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        listViewDotes.getItems().clear();
                                                        listViewDotes.getItems().addAll(action);
                                                    }
                                            )
                                            .peekLeft(error -> {
                                                a.setContentText(error);
                                                a.showAndWait();
                                            }),
                            throwable -> {
                                a = new Alert(Alert.AlertType.ERROR, ConstantesGUI.FALLO_AL_REALIZAR_LA_PETICION);
                                a.showAndWait();
                            }
                    );
            pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);

            serviciosHechizo.getHechizosByIdPersonaje(listViewPersonajes.getSelectionModel().getSelectedItem().getId())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        listViewHechizos.getItems().clear();
                                                        listViewHechizos.getItems().addAll(action);
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
    }

    @FXML
    private void updateEstadisticas() {
        if (id_Estadistica != 0 && !tfAc.getText().isEmpty() && !tfVida.getText().isEmpty() &&
                !tfFortaleza.getText().isEmpty() && !tfReflejos.getText().isEmpty() && !tfVoluntad.getText().isEmpty() &&
                !tfFuerza.getText().isEmpty() && !tfDestreza.getText().isEmpty() && !tfConstitution.getText().isEmpty() &&
                !tfInteligencia.getText().isEmpty() && !tfSabiduria.getText().isEmpty() && !tfCarisma.getText().isEmpty()) {
            serviciosEstadistica.updateEstadistica(new Estadistica(id_Estadistica,
                            Integer.parseInt(tfVida.getText()), Integer.parseInt(tfAc.getText()), Integer.parseInt(tfFortaleza.getText()),
                            Integer.parseInt(tfReflejos.getText()), Integer.parseInt(tfVoluntad.getText()), Integer.parseInt(tfFuerza.getText()),
                            Integer.parseInt(tfDestreza.getText()), Integer.parseInt(tfConstitution.getText()), Integer.parseInt(tfInteligencia.getText()),
                            Integer.parseInt(tfSabiduria.getText()), Integer.parseInt(tfCarisma.getText())))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getEstadisticas();
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
        } else {
            a.setContentText(ConstantesGUI.PRIMERO_ELIJE_UNO_DE_TUS_PERSONAJES);
            a.showAndWait();
        }

    }


}