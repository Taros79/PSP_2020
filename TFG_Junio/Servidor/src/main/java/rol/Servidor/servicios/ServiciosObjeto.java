package rol.Servidor.servicios;

import jakarta.inject.Inject;
import rol.Common.modelo.Objeto;
import rol.Common.modeloAO.RelacionId;
import rol.Servidor.dao.DaoObjeto;

import java.util.List;

public class ServiciosObjeto {

    private final DaoObjeto daoObjeto;

    @Inject
    public ServiciosObjeto(DaoObjeto daoObjeto) {
        this.daoObjeto = daoObjeto;
    }

    public List<Objeto> getAllObjetos() {
        return daoObjeto.getAllObjetos();
    }

    public String addObjeto(Objeto o) {
        return daoObjeto.addObjeto(o);
    }

    public String delObjeto(int id) {
        return daoObjeto.delObjeto(id);
    }

    public String updateObjeto(Objeto o) {
        return daoObjeto.updateObjeto(o);
    }

    public List<Objeto> getObjetosByIdPersonaje(int id) {
        return daoObjeto.getObjetosByIdPersonaje(id);
    }

    public String addObjetoToPersonaje(RelacionId r) {
        return daoObjeto.addObjetoToPersonaje(r);
    }

    public String delObjetoToPersonaje(int id_Objeto, int id2_Personaje) {
        return daoObjeto.delObjetoToPersonaje(id_Objeto, id2_Personaje);
    }
}
