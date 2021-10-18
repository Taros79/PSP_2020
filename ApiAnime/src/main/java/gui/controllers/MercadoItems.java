package gui.controllers;

import dao.modelo.ModObjetos.Objeto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import servicios.ServiciosItems;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MercadoItems implements Initializable {

    ServiciosItems serviciosItems = new ServiciosItems();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = new Alert(Alert.AlertType.INFORMATION);

        ObservableList<String> items = FXCollections.observableArrayList(
                serviciosItems.getAllItems()
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
                    imageView.setImage(new Image(serviciosItems.getItemsByNombre(name).getSprites().getJsonMemberDefault()));
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
    }

    @FXML
    private void cargarDatos(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1 && listViewItems.getSelectionModel().getSelectedItem() != null) {
            textDatosMovimiento.clear();
            textDatosMovimiento.setText(serviciosItems.getItemsByNombre(
                    listViewItems.getSelectionModel().getSelectedItem()).toString());

            textDatosMovimiento.clear();
            textDatosMovimiento.setText(
                    serviciosItems.getItemsByNombre(listViewItems.getSelectionModel().getSelectedItem()).toString()
                            + "\nDESCRICION " + serviciosItems.getItemsByNombre(listViewItems.getSelectionModel().getSelectedItem())
                            .getFlavorTextEntries()
                            .stream()
                            .filter(flavorTextEntriesItem -> flavorTextEntriesItem.getLanguage().getName().equals("es"))
                            .filter(flavorTextEntriesItem -> flavorTextEntriesItem.getVersionGroup().getName().equals("ultra-sun-ultra-moon"))
                            .collect(Collectors.toList()));

        }
    }
}
