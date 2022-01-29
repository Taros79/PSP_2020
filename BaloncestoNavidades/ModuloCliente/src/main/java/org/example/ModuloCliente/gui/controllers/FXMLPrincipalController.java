package org.example.ModuloCliente.gui.controllers;

import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import org.example.ModuloCliente.servicios.ServiciosUsuario;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class FXMLPrincipalController implements Initializable {

    private final FXMLLoader fxmlLoaderIniciarSesion;
    private final FXMLLoader fxmlLoaderRegistrarUsuario;
    private final FXMLLoader fxmlLoaderRegistroEquipo;
    private final FXMLLoader fxmlLoaderResultadoPartidos;
    private final FXMLLoader fxmlLoaderDatosLigaBaloncesto;
    private final FXMLLoader fxmlLoaderRegistroJornada;

    @FXML
    private Menu menuAdmin;
    @FXML
    private BorderPane pantallaPrincipal;

    private AnchorPane pantallaIniciarSesion;
    private IniciarSesion iniciarSesionController;
    private AnchorPane pantallaRegistrarUsuario;
    private RegistrarUsuario registrarUsuarioController;
    private AnchorPane pantallaRegistroEquipo;
    private AdministracionEquipos administracionEquiposController;
    private AnchorPane pantallaResultadoPartidos;
    private AdministracionPartidos administracionPartidosController;
    private AnchorPane pantallaDatosLigaBaloncesto;
    private DatosLigaBaloncesto datosLigaBaloncestoController;
    private AnchorPane pantallaRegistroJornada;
    private AdministracionJornadas administracionJornadasController;

    @Inject
    public FXMLPrincipalController(FXMLLoader fxmlLoaderIniciarSesion, FXMLLoader fxmlLoaderRegistrarUsuario,
                                   FXMLLoader fxmlLoaderRegistroEquipo, FXMLLoader fxmlLoaderResultadoPartidos,
                                   FXMLLoader fxmlLoaderDatosLigaBaloncesto, FXMLLoader fxmlLoaderRegistroJornada,
                                   ServiciosUsuario serviciosUsuario) {
        this.fxmlLoaderIniciarSesion = fxmlLoaderIniciarSesion;
        this.fxmlLoaderRegistrarUsuario = fxmlLoaderRegistrarUsuario;
        this.fxmlLoaderRegistroEquipo = fxmlLoaderRegistroEquipo;
        this.fxmlLoaderResultadoPartidos = fxmlLoaderResultadoPartidos;
        this.fxmlLoaderDatosLigaBaloncesto = fxmlLoaderDatosLigaBaloncesto;
        this.fxmlLoaderRegistroJornada = fxmlLoaderRegistroJornada;
    }

    public BorderPane getPantallaPrincipal() {
        return pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadIniciarSesion();
        preloadRegistrarUsuario();
        preloadRegistroEquipo();
        preloadResultadoPartidos();
        preloadRegistrarJornada();
    }

    @FXML
    private void preloadIniciarSesion() {
        if (pantallaIniciarSesion == null) {
            try {
                pantallaIniciarSesion = fxmlLoaderIniciarSesion.load(getClass()
                        .getResourceAsStream("/fxml/iniciarSesion.fxml"));
                iniciarSesionController = fxmlLoaderIniciarSesion.getController();
                iniciarSesionController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @FXML
    private void preloadRegistrarUsuario() {
        if (pantallaRegistrarUsuario == null) {
            try {
                pantallaRegistrarUsuario = fxmlLoaderRegistrarUsuario.load(getClass()
                        .getResourceAsStream("/fxml/registrarUsuario.fxml"));
                registrarUsuarioController = fxmlLoaderRegistrarUsuario.getController();
                registrarUsuarioController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @FXML
    private void preloadRegistroEquipo() {
        if (pantallaRegistroEquipo == null) {
            try {
                pantallaRegistroEquipo = fxmlLoaderRegistroEquipo.load(getClass()
                        .getResourceAsStream("/fxml/administracionEquipos.fxml"));
                administracionEquiposController = fxmlLoaderRegistroEquipo.getController();
                administracionEquiposController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @FXML
    private void preloadResultadoPartidos() {
        if (pantallaResultadoPartidos == null) {
            try {
                pantallaResultadoPartidos = fxmlLoaderResultadoPartidos.load(getClass()
                        .getResourceAsStream("/fxml/administracionPartidos.fxml"));
                administracionPartidosController = fxmlLoaderResultadoPartidos.getController();
                administracionPartidosController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @FXML
    private void preloadDatosLigaBaloncesto() {
        if (pantallaResultadoPartidos == null) {
            try {
                pantallaDatosLigaBaloncesto = fxmlLoaderDatosLigaBaloncesto.load(getClass()
                        .getResourceAsStream("/fxml/datosLigaBaloncesto.fxml"));
                datosLigaBaloncestoController = fxmlLoaderDatosLigaBaloncesto.getController();
                datosLigaBaloncestoController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @FXML
    private void iniciarSesion() {
        pantallaPrincipal.setCenter(pantallaIniciarSesion);
    }

    @FXML
    private void registrarUsuario() {
        pantallaPrincipal.setCenter(pantallaRegistrarUsuario);
        registrarUsuarioController.actualizar();
    }

    @FXML
    private void registrarEquipo() {
        pantallaPrincipal.setCenter(pantallaRegistroEquipo);
        administracionEquiposController.actualizar();
    }

    @FXML
    private void resultadoPartidos() {
        pantallaPrincipal.setCenter(pantallaResultadoPartidos);
        administracionPartidosController.actualizar();
    }

    @FXML
    private void datosLigaBaloncesto() {
        pantallaPrincipal.setCenter(pantallaDatosLigaBaloncesto);
    }


    public void activarAdmin() {
        menuAdmin.setVisible(true);
        registrarUsuarioController.activarAdmin();
        registrarUsuario();
    }

    //----------------------------------

    @FXML
    private void preloadRegistrarJornada() {
        if (pantallaRegistroJornada == null) {
            try {
                pantallaRegistroJornada = fxmlLoaderRegistroJornada.load(getClass()
                        .getResourceAsStream("/fxml/administracionJornadas.fxml"));
                administracionJornadasController = fxmlLoaderRegistroJornada.getController();
                administracionJornadasController.setPantallaPrincipal(this);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @FXML
    private void registrarJornada() {
        pantallaPrincipal.setCenter(pantallaRegistroJornada);
        administracionJornadasController.actualizar();
    }

}