package rol.Cliente.gui.controllers.complements;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import rol.Cliente.Servicios.ServiciosDote;
import rol.Cliente.Servicios.ServiciosHechizo;
import rol.Cliente.Servicios.ServiciosObjeto;
import rol.Cliente.gui.ConstantesGUI;
import rol.Cliente.gui.controllers.FXMLPrincipalController;
import rol.Common.modelo.Dote;
import rol.Common.modelo.Hechizo;
import rol.Common.modelo.Objeto;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

import static rol.Cliente.gui.utils.OnlyNumbers.onlyNum;


public class CrudHOD implements Initializable {

    @FXML
    private TextField tfNombreHechizo;
    @FXML
    private TextField tfDescripcionHechizo;
    @FXML
    private TextField tfNivelHechizo;
    @FXML
    private TextField tfNombreDote;
    @FXML
    private TextField tfDescripcionDote;
    @FXML
    private TextField tfNombreObjeto;
    @FXML
    private TextField tfDescripcionObjeto;
    @FXML
    private TextField tfRanuraObjeto;
    @FXML
    private TextField tfNivelObjeto;
    @FXML
    private TextField tfPesoObjeto;
    @FXML
    private TextField tfPrecioObjeto;
    @FXML
    private ListView<Dote> listViewAllDotes;
    @FXML
    private ListView<Objeto> listViewAllObjetos;
    @FXML
    private ListView<Hechizo> listViewAllHechizos;

    private FXMLPrincipalController pantallaPrincipal;
    private Alert a;
    private ServiciosDote serviciosDote;
    private ServiciosHechizo serviciosHechizo;
    private ServiciosObjeto serviciosObjeto;

    @Inject
    public CrudHOD(ServiciosDote serviciosDote, ServiciosHechizo serviciosHechizo, ServiciosObjeto serviciosObjeto) {
        this.serviciosDote = serviciosDote;
        this.serviciosHechizo = serviciosHechizo;
        this.serviciosObjeto = serviciosObjeto;
    }

    public void setPerfil(FXMLPrincipalController pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);

        onlyNum(tfNivelHechizo);
        onlyNum(tfNivelObjeto);
        onlyNum(tfPesoObjeto);
        onlyNum(tfPrecioObjeto);
    }

    private void getDotes() {
        listViewAllDotes.getItems().clear();
        serviciosDote.getAllDotes()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {

                                                    listViewAllDotes.getItems().addAll(action);
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

    private void getHechizos() {
        listViewAllHechizos.getItems().clear();
        serviciosHechizo.getAllHechizos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {

                                                    listViewAllHechizos.getItems().addAll(action);
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

    private void getObjetos() {
        listViewAllObjetos.getItems().clear();
        serviciosObjeto.getAllObjetos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(resultado ->
                                resultado
                                        .peek(action -> {

                                                    listViewAllObjetos.getItems().addAll(action);
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
        getDotes();
        getHechizos();
        getObjetos();
    }

    @FXML
    private void handleMouseClickDotes(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewAllDotes.getSelectionModel().getSelectedItem() != null) {
            tfNombreDote.setText(listViewAllDotes.getSelectionModel().getSelectedItem().getNombre());
            tfDescripcionDote.setText(listViewAllDotes.getSelectionModel().getSelectedItem().getDescripcion());
        }
    }

    @FXML
    private void handleMouseClickHechizos(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewAllHechizos.getSelectionModel().getSelectedItem() != null) {
            tfNombreHechizo.setText(listViewAllHechizos.getSelectionModel().getSelectedItem().getNombre());
            tfDescripcionHechizo.setText(listViewAllHechizos.getSelectionModel().getSelectedItem().getDescripcion());
            tfNivelHechizo.setText(String.valueOf(listViewAllHechizos.getSelectionModel().getSelectedItem().getNivel()));
        }
    }

    @FXML
    private void handleMouseClickObjetos(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewAllObjetos.getSelectionModel().getSelectedItem() != null) {
            tfNombreObjeto.setText(listViewAllObjetos.getSelectionModel().getSelectedItem().getNombre());
            tfDescripcionObjeto.setText(listViewAllObjetos.getSelectionModel().getSelectedItem().getDescripcion());
            tfRanuraObjeto.setText(listViewAllObjetos.getSelectionModel().getSelectedItem().getRanura());
            tfNivelObjeto.setText(String.valueOf(listViewAllObjetos.getSelectionModel().getSelectedItem().getNivel()));
            tfPesoObjeto.setText(String.valueOf(listViewAllObjetos.getSelectionModel().getSelectedItem().getPeso()));
            tfPrecioObjeto.setText(String.valueOf(listViewAllObjetos.getSelectionModel().getSelectedItem().getPrecio()));
        }
    }

    @FXML
    private void addHechizo() {
        if (!tfNombreHechizo.getText().isEmpty() || !tfDescripcionHechizo.getText().isEmpty() ||
                !tfNivelHechizo.getText().isEmpty()) {
            var tarea = new Task<Either<String, String>>() {
                @Override
                protected Either<String, String> call() {
                    return serviciosHechizo.addHechizo(new Hechizo(tfNombreHechizo.getText(),
                            tfDescripcionHechizo.getText(), Integer.parseInt(tfNivelHechizo.getText())));
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(s -> {
                                    getHechizos();
                                    a = new Alert(Alert.AlertType.INFORMATION, s);
                                    a.showAndWait();
                                })
                                .peekLeft(s -> {
                                    a.setContentText(s);
                                    a.showAndWait();
                                }))
                        .onFailure(throwable -> {
                            a.setContentText(throwable.getMessage());
                            a.showAndWait();
                        });
                pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                a.setContentText(workerStateEvent.getSource().getException().getMessage());
                a.showAndWait();
                pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.CAMPOS_VACIOS);
            a.showAndWait();
        }
    }

    @FXML
    private void delHechizo() {
        if (!listViewAllHechizos.getSelectionModel().isEmpty()) {
            Single<Either<String, String>> singleEquipo = Single.fromCallable(() ->
                            serviciosHechizo.delHechizo(listViewAllHechizos.getSelectionModel().getSelectedItem().getId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT));

            singleEquipo.subscribe(either -> either
                            .peek(e -> {
                                getHechizos();
                                a.setContentText(e);
                                a.showAndWait();
                            })
                            .peekLeft(fail -> {
                                a.setContentText(fail);
                                a.showAndWait();
                            }),
                    throwable -> {
                        a.setContentText(String.valueOf(throwable));
                        a.showAndWait();
                    });

            pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.SELECCIONA_HECHIZO_DE_LA_LISTA);
            a.showAndWait();
        }
    }

    @FXML
    private void updHechizo() {
        if (!listViewAllHechizos.getSelectionModel().isEmpty()) {
            serviciosHechizo.updateHechizo(new Hechizo(listViewAllHechizos.getSelectionModel().getSelectedItem().getId(),
                            tfNombreHechizo.getText(), tfDescripcionHechizo.getText(), Integer.parseInt(tfNivelHechizo.getText())))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getHechizos();
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
            a.setContentText(ConstantesGUI.SELECCIONA_HECHIZO_DE_LA_LISTA);
            a.showAndWait();
        }
    }

    @FXML
    private void addDote() {
        if (!tfNombreDote.getText().isEmpty() || !tfDescripcionDote.getText().isEmpty()) {
            var tarea = new Task<Either<String, String>>() {
                @Override
                protected Either<String, String> call() {
                    return serviciosDote.addDote(new Dote(tfNombreDote.getText(),
                            tfDescripcionDote.getText()));
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(s -> {
                                    getDotes();
                                    a = new Alert(Alert.AlertType.INFORMATION, s);
                                    a.showAndWait();
                                })
                                .peekLeft(s -> {
                                    a.setContentText(s);
                                    a.showAndWait();
                                }))
                        .onFailure(throwable -> {
                            a.setContentText(throwable.getMessage());
                            a.showAndWait();
                        });
                pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                a.setContentText(workerStateEvent.getSource().getException().getMessage());
                a.showAndWait();
                pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.CAMPOS_VACIOS);
            a.showAndWait();
        }
    }

    @FXML
    private void delDote() {
        if (!listViewAllDotes.getSelectionModel().isEmpty()) {
            Single<Either<String, String>> singleEquipo = Single.fromCallable(() ->
                            serviciosDote.delDote(listViewAllDotes.getSelectionModel().getSelectedItem().getId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT));

            singleEquipo.subscribe(either -> either
                            .peek(e -> {
                                getDotes();
                                a = new Alert(Alert.AlertType.INFORMATION, e);
                                a.showAndWait();
                            })
                            .peekLeft(fail -> {
                                a.setContentText(fail);
                                a.showAndWait();
                            }),
                    throwable -> {
                        a.setContentText(String.valueOf(throwable));
                        a.showAndWait();
                    });

            pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.SELECCIONA_DOTE_DE_LA_LISTA);
            a.showAndWait();
        }
    }

    @FXML
    private void updDote() {
        if (!listViewAllDotes.getSelectionModel().isEmpty()) {
            serviciosDote.updateDote(new Dote(listViewAllDotes.getSelectionModel().getSelectedItem().getId(),
                            tfNombreDote.getText(), tfDescripcionDote.getText()))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getDotes();
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
            a.setContentText(ConstantesGUI.SELECCIONA_DOTE_DE_LA_LISTA);
            a.showAndWait();
        }
    }

    @FXML
    private void addObjeto() {
        if (!tfNombreObjeto.getText().isEmpty() || !tfDescripcionObjeto.getText().isEmpty() ||
                !tfRanuraObjeto.getText().isEmpty() || !tfNivelObjeto.getText().isEmpty() ||
                !tfPesoObjeto.getText().isEmpty() || !tfPrecioObjeto.getText().isEmpty()) {
            var tarea = new Task<Either<String, String>>() {
                @Override
                protected Either<String, String> call() {
                    return serviciosObjeto.addObjeto(new Objeto(tfNombreObjeto.getText(),
                            tfDescripcionObjeto.getText(), tfRanuraObjeto.getText(),
                            Integer.parseInt(tfNivelObjeto.getText()), Integer.parseInt(tfPesoObjeto.getText()),
                            Double.parseDouble(tfPrecioObjeto.getText())));
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(s -> {
                                    getObjetos();
                                    a = new Alert(Alert.AlertType.INFORMATION, s);
                                    a.showAndWait();
                                })
                                .peekLeft(s -> {
                                    a.setContentText(s);
                                    a.showAndWait();
                                }))
                        .onFailure(throwable -> {
                            a.setContentText(throwable.getMessage());
                            a.showAndWait();
                        });
                pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                a.setContentText(workerStateEvent.getSource().getException().getMessage());
                a.showAndWait();
                pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.CAMPOS_VACIOS);
            a.showAndWait();
        }
    }

    @FXML
    private void delObjeto() {
        if (!listViewAllObjetos.getSelectionModel().isEmpty()) {
            Single<Either<String, String>> singleEquipo = Single.fromCallable(() ->
                            serviciosObjeto.delObjeto(listViewAllObjetos.getSelectionModel().getSelectedItem().getId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT));

            singleEquipo.subscribe(either -> either
                            .peek(e -> {
                                getObjetos();
                                a = new Alert(Alert.AlertType.INFORMATION, e);
                                a.showAndWait();
                            })
                            .peekLeft(fail -> {
                                a.setContentText(fail);
                                a.showAndWait();
                            }),
                    throwable -> {
                        a.setContentText(String.valueOf(throwable));
                        a.showAndWait();
                    });

            pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.WAIT);
        } else {
            a.setContentText(ConstantesGUI.SELECCIONA_OBJETO_DE_LA_LISTA);
            a.showAndWait();
        }
    }

    @FXML
    private void updObjeto() {
        if (!listViewAllObjetos.getSelectionModel().isEmpty()) {
            serviciosObjeto.updateObjeto(new Objeto(listViewAllObjetos.getSelectionModel().getSelectedItem().getId(),
                            tfNombreObjeto.getText(), tfDescripcionObjeto.getText(), tfRanuraObjeto.getText(),
                            Integer.parseInt(tfNivelObjeto.getText()), Integer.parseInt(tfPesoObjeto.getText()),
                            Double.parseDouble(tfPrecioObjeto.getText())))
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> this.pantallaPrincipal.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(resultado ->
                                    resultado
                                            .peek(action -> {
                                                        getObjetos();
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
            a.setContentText(ConstantesGUI.SELECCIONA_OBJETO_DE_LA_LISTA);
            a.showAndWait();
        }
    }
}