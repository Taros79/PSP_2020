package rol.Servidor.servicios;

import jakarta.inject.Inject;
import rol.Common.modelo.Hechizo;
import rol.Common.modeloAO.RelacionId;
import rol.Servidor.dao.DaoHechizo;

import java.util.List;

public class ServiciosHechizo {

    private final DaoHechizo daoHechizo;

    @Inject
    public ServiciosHechizo(DaoHechizo daoHechizo) {
        this.daoHechizo = daoHechizo;
    }

    public List<Hechizo> getAllHechizo() {
        return daoHechizo.getAllHechizo();
    }

    public String addHechizo(Hechizo h) {
        return daoHechizo.addHechizo(h);
    }

    public String delHechizo(int id) {
        return daoHechizo.delHechizo(id);
    }

    public String updateHechizo(Hechizo h) {
        return daoHechizo.updateHechizo(h);
    }

    public List<Hechizo> getHechizosByIdPersonaje(int id) {
        return daoHechizo.getHechizosByIdPersonaje(id);
    }

    public String addHechizoToPersonaje(RelacionId r) {
        return daoHechizo.addHechizoToPersonaje(r);
    }

    public String delHechizoToPersonaje(int id_Hechizo, int id2_Personaje) {
        return daoHechizo.delHechizoToPersonaje(id_Hechizo, id2_Personaje);
    }
}
