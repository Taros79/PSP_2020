package rol.Cliente.gui.controllers.complements;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import rol.Cliente.Servicios.ServiciosEstadistica;
import rol.Cliente.Servicios.ServiciosPersonaje;
import rol.Cliente.gui.ConstantesGUI;
import rol.Cliente.gui.controllers.FXMLPrincipalController;
import rol.Common.modelo.Estadistica;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.PersonajeBBDD;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

import static rol.Cliente.gui.utils.OnlyNumbers.onlyNum;


public class Personajes implements Initializable {

    @FXML
    private TextField tfRaza;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfClase;
    @FXML
    private TextField tfAlin;
    @FXML
    private TextField tfNivel;
    @FXML
    private TextField tfExp;
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
    private ListView<Personaje> listViewPersonajes;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosPersonaje serviciosPersonaje;
    private ServiciosEstadistica serviciosEstadistica;


    @Inject
    public Personajes(ServiciosPersonaje serviciosPersonaje, ServiciosEstadistica serviciosEstadistica) {
        this.serviciosPersonaje = serviciosPersonaje;
        this.serviciosEstadistica = serviciosEstadistica;

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
        onlyNum(tfNivel);
        onlyNum(tfExp);
    }

    private void getEstadisticas() {
        var personaje = listViewPersonajes.getSelectionModel().getSelectedItem();
        serviciosEstadistica.getEstadisticaById(personaje.getId_Estadistica())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {
                                                    tfNombre.setText(personaje.getNombre());
                                                    tfRaza.setText(personaje.getRaza());
                                                    tfClase.setText(personaje.getClase());
                                                    tfAlin.setText(personaje.getAlineamiento());
                                                    tfNivel.setText(String.valueOf(personaje.getNivel()));
                                                    tfExp.setText(String.valueOf(personaje.getExperiencia()));
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
                                                    tfNombre.clear();
                                                    tfRaza.clear();
                                                    tfClase.clear();
                                                    tfAlin.clear();
                                                    tfNivel.clear();
                                                    tfExp.clear();
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
        }
    }

    @FXML
    private void addPersonaje() {
        if (!tfNombre.getText().isEmpty() && !tfRaza.getText().isEmpty() && !tfClase.getText().isEmpty() &&
                !tfAlin.getText().isEmpty() && !tfNivel.getText().isEmpty() && !tfExp.getText().isEmpty() &&
                !tfVida.getText().isEmpty() && !tfAc.getText().isEmpty() && !tfFortaleza.getText().isEmpty() &&
                !tfReflejos.getText().isEmpty() && !tfVoluntad.getText().isEmpty() && !tfFuerza.getText().isEmpty() &&
                !tfDestreza.getText().isEmpty() && !tfConstitution.getText().isEmpty() && !tfInteligencia.getText().isEmpty() &&
                !tfSabiduria.getText().isEmpty() && !tfCarisma.getText().isEmpty()) {
            PersonajeBBDD personaje = new PersonajeBBDD(
                    new Personaje(tfNombre.getText(), tfRaza.getText(), tfClase.getText(), tfAlin.getText(),
                            Integer.parseInt(tfNivel.getText()), Integer.parseInt(tfExp.getText())),
                    new Estadistica(Integer.parseInt(tfVida.getText()), Integer.parseInt(tfAc.getText()),
                            Integer.parseInt(tfFortaleza.getText()), Integer.parseInt(tfReflejos.getText()),
                            Integer.parseInt(tfVoluntad.getText()), Integer.parseInt(tfFuerza.getText()),
                            Integer.parseInt(tfDestreza.getText()), Integer.parseInt(tfConstitution.getText()),
                            Integer.parseInt(tfInteligencia.getText()), Integer.parseInt(tfSabiduria.getText()),
                            Integer.parseInt(tfCarisma.getText())));

            serviciosPersonaje.addPersonajeToUsuario(personaje)
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        actualizarDatos();
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
            a.setContentText(ConstantesGUI.CAMPOS_VACIOS);
            a.showAndWait();
        }
    }

    @FXML
    private void delPersonaje() {
        if (!listViewPersonajes.getSelectionModel().isEmpty()) {
            serviciosPersonaje.delPersonaje(listViewPersonajes.getSelectionModel().getSelectedItem().getId(),
                            listViewPersonajes.getSelectionModel().getSelectedItem().getId_Estadistica())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        actualizarDatos();
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
            a.setContentText(ConstantesGUI.SELECCIONA_PERSONAJE_DE_LA_LISTA);
            a.showAndWait();
        }
    }
}