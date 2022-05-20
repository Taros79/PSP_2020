package gui.controllers;

import dao.Encriptaciones;
import dao.modelo.Secreto;
import dao.modelo.SecretoCompartido;
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
import javafx.scene.input.MouseEvent;
import servicios.ServiciosUsuario;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Secretitos implements Initializable {


    @FXML
    private ComboBox<Usuario> comboBoxUserDestino;
    @FXML
    private ComboBox<Secreto> comboBoxSecretoUser;
    @FXML
    private ListView<SecretoCompartido> listViewAllSecret;
    @FXML
    private TextField textNameSecretAdd;
    @FXML
    private TextField textPassAdd;
    @FXML
    private TextField textSecretAdd;
    @FXML
    private TextField textUserAdd;
    @FXML
    private ComboBox<Usuario> comboBoxUser;
    @FXML
    private FXMLPrincipalController pantallaPrincipal;

    private Alert a;
    private ServiciosUsuario serviciosUsuario;
    private Encriptaciones encriptaciones;

    @Inject
    public Secretitos(ServiciosUsuario serviciosUsuario, Encriptaciones encriptaciones) {
        this.serviciosUsuario = serviciosUsuario;
        this.encriptaciones = encriptaciones;
    }

    public void setPantallaPrincipal(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        a = new Alert(Alert.AlertType.INFORMATION);
    }


    @FXML
    private void addSecretAddUser() {
        if (!textPassAdd.getText().isEmpty() && !textSecretAdd.getText().isEmpty() && !textUserAdd.getText().isEmpty()) {
            Usuario u = new Usuario(textUserAdd.getText(), textPassAdd.getText());
            Secreto s = new Secreto(textNameSecretAdd.getText(), textSecretAdd.getText());
            var tarea = new Task<Either<String, Usuario>>() {
                @Override
                protected Either<String, Usuario> call() {
                    return serviciosUsuario.addUsuario(s, u);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(usuario -> {
                                    a.setContentText(usuario + ConstantesGUI.CREADO_CON_EXITO);
                                    a.showAndWait();
                                    actualizar();
                                })
                                .peekLeft(ss -> {
                                    a.setContentText(String.valueOf(ss));
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

    public void getSecretosCompartidos() {
        var tarea = new Task<Either<String, List<SecretoCompartido>>>() {
            @Override
            protected Either<String, List<SecretoCompartido>> call() {
                return serviciosUsuario.getSecretosCompartidos();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            listViewAllSecret.getItems().clear();
            Try.of(() -> tarea.get().peek(secretoCompartidos -> listViewAllSecret.getItems().addAll(secretoCompartidos))
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
    private void seeSecret() {
        if (!comboBoxUser.getSelectionModel().isEmpty() && !listViewAllSecret.getSelectionModel().isEmpty()) {
            var tarea = new Task<Either<String, String>>() {
                @Override
                protected Either<String, String> call() {
                    return encriptaciones.desencriptarRSAClaveCifrada(
                            listViewAllSecret.getSelectionModel().getSelectedItem(),
                            comboBoxUser.getSelectionModel().getSelectedItem());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(s -> {
                                    var mensaje = encriptaciones.desencriptarAESTextoConRandom(serviciosUsuario.getSecretoPorID(
                                            listViewAllSecret.getSelectionModel().getSelectedItem().getIdSecreto()).get(), s).get();
                                    a.setContentText(mensaje);
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
    private void getUsersCombo() {
        var tarea = new Task<Either<String, List<Usuario>>>() {
            @Override
            protected Either<String, List<Usuario>> call() {
                return serviciosUsuario.getUsuarios();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            comboBoxUser.getItems().clear();
            Try.of(() -> tarea.get().peek(usuarios -> {
                                comboBoxUser.getItems().addAll(usuarios);
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
    }

    public void getSecretosCombo() {
        var tarea = new Task<Either<String, List<Secreto>>>() {
            @Override
            protected Either<String, List<Secreto>> call() throws Exception {
                return serviciosUsuario.getSecretos();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            comboBoxSecretoUser.getItems().clear();
            Try.of(() -> tarea.get().peek(secretos -> comboBoxSecretoUser.getItems().addAll(secretos))
                            .peekLeft(s -> {
                                a.setContentText(String.valueOf(s));
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
    private void compartirSecreto() {
        if (!listViewAllSecret.getSelectionModel().isEmpty() && !comboBoxUser.getSelectionModel().isEmpty() && !comboBoxUserDestino.getSelectionModel().isEmpty() && !comboBoxSecretoUser.getSelectionModel().isEmpty()) {

            var tarea = new Task<Either<String, String>>() {
                @Override
                protected Either<String, String> call() {
                    return serviciosUsuario.compartirSecreto(listViewAllSecret.getSelectionModel().getSelectedItem(),
                            comboBoxUser.getSelectionModel().getSelectedItem(),
                            comboBoxUserDestino.getSelectionModel().getSelectedItem());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(s1 -> {
                                    a.setContentText(s1);
                                    a.showAndWait();
                                    actualizar();
                                })
                                .peekLeft(s1 -> {
                                    a.setContentText(String.valueOf(s1));
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
            a.setContentText(ConstantesGUI.DEBES_COMPLETAR_CAMPOS);
            a.showAndWait();
        }
    }

    private void actualizar() {
        textUserAdd.clear();
        textPassAdd.clear();
        textSecretAdd.clear();
        textNameSecretAdd.clear();
        comboBoxUser.getSelectionModel().selectFirst();
        listViewAllSecret.getItems().clear();
        listViewAllSecret.getItems().addAll(serviciosUsuario.getSecretosCompartidos().get());
        comboBoxSecretoUser.getItems().clear();
        comboBoxSecretoUser.getItems().addAll(serviciosUsuario.getSecretos().get());
    }

    @FXML
    private void getUsersDestinoCombo(MouseEvent mouseEvent) {
        var tarea = new Task<Either<String, List<Usuario>>>() {
            @Override
            protected Either<String, List<Usuario>> call() {
                return serviciosUsuario.getUsuarios();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            comboBoxUserDestino.getItems().clear();
            Try.of(() -> tarea.get().peek(usuarios -> {
                                comboBoxUserDestino.getItems().addAll(usuarios);
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
    }
}
