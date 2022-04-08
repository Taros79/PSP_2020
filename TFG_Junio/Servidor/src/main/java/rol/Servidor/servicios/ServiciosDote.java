package rol.Servidor.servicios;

import jakarta.inject.Inject;
import rol.Common.modelo.Dote;
import rol.Common.modeloAO.RelacionId;
import rol.Servidor.dao.DaoDote;

import java.util.List;

public class ServiciosDote {

    private final DaoDote daoDote;

    @Inject
    public ServiciosDote(DaoDote daoDote) {
        this.daoDote = daoDote;
    }

    public List<Dote> getAllDotes() {
        return daoDote.getAllDotes();
    }

    public String addDote(Dote d) {
        return daoDote.addDote(d);
    }

    public String delDote(int id) {
        return daoDote.delDote(id);
    }

    public String updateDote(Dote d) {
        return daoDote.updateDote(d);
    }

    public List<Dote> getDotesByIdPersonaje(int id) {
        return daoDote.getDotesByIdPersonaje(id);
    }

    public String addDoteToPersonaje(RelacionId r) {
        return daoDote.addDoteToPersonaje(r);
    }

    public String delDoteToPersonaje(int id_Dote, int id2_Personaje) {
        return daoDote.delDoteToPersonaje(id_Dote, id2_Personaje);
    }
}
