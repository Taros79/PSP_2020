package gui.controllers;

import dao.modelo.ModObjetos.Objeto;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import servicios.serviciosImplementacion.ServiciosItemsImpl;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MercadoItems implements Initializable {

    ServiciosItemsImpl serviciosItemsImpl;
    @FXML
    private TextArea textDatosMovimiento;
    @FXML
    private ListView<String> listViewItems;
    @FXML
    private PantallaPrincipal pantallaPrincipal;
    private Alert a;

    public void setBorderPane(PantallaPrincipal borderPane) {
        this.pantallaPrincipal = borderPane;
    }

    @Inject
    public MercadoItems(ServiciosItemsImpl serviciosItemsImpl) {
        this.serviciosItemsImpl = serviciosItemsImpl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);
        if (serviciosItemsImpl.getAllItems().isRight()) {
            ObservableList<String> items = FXCollections.observableArrayList(
                    serviciosItemsImpl.getAllItems().get()
                            .stream().map(Objeto::getName).collect(Collectors.toList()));

            listViewItems.setItems(items);

            listViewItems.setCellFactory(param -> new ListCell<>() {
                private final ImageView imageView = new ImageView();

                @Override
                public void updateItem(String name, boolean empty) {
                    super.updateItem(name, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        imageView.setImage(new Image(serviciosItemsImpl.getItemsByNombre(name).get()
                                .getSprites().getJsonMemberDefault()));
                        setText(name);
                        setGraphic(imageView);
                    }
                }
            });
        } else {
            a.setContentText(serviciosItemsImpl.getAllItems().getLeft());
            a.showAndWait();
        }
    }

    @FXML
    private void cargarDatos(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewItems.getSelectionModel().getSelectedItem() != null) {
            var tarea = new Task<Either<String, Objeto>>() {
                @Override
                protected Either<String, Objeto> call() {
                    return serviciosItemsImpl.getItemsByNombre(listViewItems.getSelectionModel()
                            .getSelectedItem());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get()
                                .peek(objeto -> textDatosMovimiento.setText(objeto.toString()
                                        + "\nDESCRICION " + serviciosItemsImpl.getItemsByNombre(
                                                listViewItems.getSelectionModel().getSelectedItem()).get()
                                        .getFlavorTextEntries()
                                        .stream()
                                        .filter(flavorTextEntriesItem -> flavorTextEntriesItem.getLanguage().getName().equals("es"))
                                        .filter(flavorTextEntriesItem -> flavorTextEntriesItem.getVersionGroup().getName().equals("ultra-sun-ultra-moon"))
                                        .collect(Collectors.toList()))
                                )
                                .peekLeft(s -> {
                                    a.setContentText(s);
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
}
