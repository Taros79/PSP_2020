package rol.Servidor.servicios;

import jakarta.inject.Inject;
import rol.Common.modelo.Personaje;
import rol.Common.modeloAO.PersonajeBBDD;
import rol.Servidor.dao.DaoPersonaje;

import java.util.List;

public class ServiciosPersonaje {

    private final DaoPersonaje daoPersonaje;

    @Inject
    public ServiciosPersonaje(DaoPersonaje daoPersonaje) {
        this.daoPersonaje = daoPersonaje;
    }

    public List<Personaje> getAllPersonajes() {
        return daoPersonaje.getAllPersonajes();
    }

    public String addPersonaje(PersonajeBBDD p) {
        return daoPersonaje.addPersonaje(p);
    }

    public String delPersonaje(int id_Personaje, int id_Estadistica) {
        return daoPersonaje.delPersonaje(id_Personaje, id_Estadistica);
    }

    public String updatePersonaje(Personaje p) {
        return daoPersonaje.updatePersonaje(p);
    }

    public List<Personaje> getPersonajesByIdUsuario(int id) {
        return daoPersonaje.getPersonajesByIdUsuario(id);
    }
}
