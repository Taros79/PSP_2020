package gui.controllers;

import dao.modelo.Usuario;
import gui.utils.ConstantesGUI;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import servicios.ServiciosUsuario;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Secretitos implements Initializable {

    @FXML
    private ComboBox<Usuario> comboBoxUser;
    @FXML
    private TextField textPassAdd;
    @FXML
    private TextField textSecretAdd;
    @FXML
    private TextField textPassVer;
    @FXML
    private TextField textUserAdd;
    @FXML
    private ListView<String> listViewSecret;
    @FXML
    private FXMLPrincipalController pantallaPrincipal;

    private Alert a;
    private ServiciosUsuario serviciosUsuario;

    @Inject
    public Secretitos(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }


    @FXML
    private void addSecret() {
        if (!textPassAdd.getText().isEmpty() && !textSecretAdd.getText().isEmpty() && !textUserAdd.getText().isEmpty()) {
            Usuario u = new Usuario(textUserAdd.getText(), textSecretAdd.getText());
            var tarea = new Task<Either<String, Usuario>>() {
                @Override
                protected Either<String, Usuario> call() {
                    return serviciosUsuario.addUsuario(u, textPassAdd.getText());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(usuario -> {
                                    actualizar();
                                    a.setContentText(usuario + ConstantesGUI.CREADO_CON_EXITO);
                                    a.showAndWait();
                                })
                                .peekLeft(s -> {
                                    a.setContentText(String.valueOf(s));
                                    a.showAndWait();
                                }))
                        .onFailure(throwable -> {
                            a.setContentText(throwable.getMessage());
                            a.showAndWait();
                        });
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                a.setContentText(workerStateEvent.getSource().getException().getMessage());
                a.showAndWait();
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.RELLENAR_DATOS);
            a.showAndWait();
        }
    }

    @FXML
    private void seeSecret() {
        if (!comboBoxUser.getSelectionModel().isEmpty() && !textPassVer.getText().isEmpty()) {
            var tarea = new Task<Either<String, String>>() {
                @Override
                protected Either<String, String> call() {
                    return serviciosUsuario.getSecretos(comboBoxUser.getSelectionModel().getSelectedItem().getNombre(),
                            textPassVer.getText());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                listViewSecret.getItems().clear();
                Try.of(() -> tarea.get().peek(secretos -> {
                                    actualizar();
                                    listViewSecret.getItems().addAll(secretos);
                                })
                                .peekLeft(s -> {
                                    a.setContentText(String.valueOf(s));
                                    a.showAndWait();
                                }))
                        .onFailure(throwable -> {
                            a.setContentText(throwable.getMessage());
                            a.showAndWait();
                        });
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                a.setContentText(workerStateEvent.getSource().getException().getMessage());
                a.showAndWait();
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.RELLENAR_DATOS);
            a.showAndWait();
        }
    }

    @FXML
    private void getUsersCombo() {
        var tarea = new Task<Either<String, List<Usuario>>>() {
            @Override
            protected Either<String, List<Usuario>> call() {
                return serviciosUsuario.getUsuarios();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            comboBoxUser.getItems().clear();
            Try.of(() -> tarea.get().peek(usuarios -> comboBoxUser.getItems().addAll(usuarios))
                            .peekLeft(s -> {
                                a.setContentText(String.valueOf(s));
                                a.showAndWait();
                            }))
                    .onFailure(throwable -> {
                        a.setContentText(throwable.getMessage());
                        a.showAndWait();
                    });
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        tarea.setOnFailed(workerStateEvent -> {
            a.setContentText(workerStateEvent.getSource().getException().getMessage());
            a.showAndWait();
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
        });
        new Thread(tarea).start();
        this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    @FXML
    private void seeSecretByPass() {
        if (!textPassVer.getText().isEmpty()) {
            var tarea = new Task<Either<String, List<String>>>() {
                @Override
                protected Either<String, List<String>> call() {
                    return serviciosUsuario.getSecretosPorPass(textPassVer.getText());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                listViewSecret.getItems().clear();
                Try.of(() -> tarea.get().peek(secretos -> {
                                    actualizar();
                                    listViewSecret.getItems().addAll(secretos);
                                })
                                .peekLeft(s -> {
                                    a.setContentText(String.valueOf(s));
                                    a.showAndWait();
                                }))
                        .onFailure(throwable -> {
                            a.setContentText(throwable.getMessage());
                            a.showAndWait();
                        });
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                a.setContentText(workerStateEvent.getSource().getException().getMessage());
                a.showAndWait();
                this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.RELLENAR_DATOS_ALL);
            a.showAndWait();
        }
    }

    private void actualizar() {
        textUserAdd.clear();
        textPassAdd.clear();
        textSecretAdd.clear();
        textPassVer.clear();
        listViewSecret.getItems().clear();
        comboBoxUser.getSelectionModel().clearSelection();
    }
}
