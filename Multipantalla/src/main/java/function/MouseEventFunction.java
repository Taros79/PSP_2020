package function;

import dao.modelo.Persona;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class MouseEventFunction {

    public static void mouseClick(javafx.scene.input.MouseEvent mouseEvent, ListView<Persona> listViewUsuarios, TextField txtNombre, TextField txtEdad, RadioButton rbSi, RadioButton rbNo, RadioButton rbDepende) {
        if (mouseEvent.getClickCount() == 1 && listViewUsuarios.getSelectionModel().getSelectedItem() != null) {
            txtNombre.setText(listViewUsuarios.getSelectionModel().getSelectedItem().getNombre());
            txtEdad.setText(String.valueOf(listViewUsuarios.getSelectionModel().getSelectedItem().getEdad()));
            if (rbDepende.isSelected()){
                rbDepende.fire();
            }
            if(listViewUsuarios.getSelectionModel().getSelectedItem().isMujer() & !rbSi.isSelected()){
                rbSi.selectedProperty().setValue(true);
                rbNo.selectedProperty().setValue(false);
            }else if(!listViewUsuarios.getSelectionModel().getSelectedItem().isMujer() & !rbNo.isSelected()){
                rbNo.selectedProperty().setValue(true);
                rbSi.selectedProperty().setValue(false);
            }

        }
    }
}
