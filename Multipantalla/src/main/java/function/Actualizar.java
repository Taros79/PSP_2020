package function;

import dao.modelo.Persona;
import javafx.scene.control.ListView;
import servicios.persona.ServicioGetPersona;

public class Actualizar {

    public static void actualizarLView(ListView<Persona>listViewUsuarios){
        ServicioGetPersona sgp = new ServicioGetPersona();
        listViewUsuarios.getItems().clear();
        listViewUsuarios.getItems().addAll(sgp.getPersonas());
    }
}
