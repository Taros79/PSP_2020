package rol.Servidor.servicios;

import jakarta.inject.Inject;
import rol.Common.modelo.Partida;
import rol.Servidor.dao.DaoPartida;

import java.util.List;

public class ServiciosPartida {

    private final DaoPartida daoPartida;

    @Inject
    public ServiciosPartida(DaoPartida daoPartida) {
        this.daoPartida = daoPartida;
    }

    public List<Partida> getAllPartidasByMaster(int idMaster) {
        return daoPartida.getAllPartidasByMaster(idMaster);
    }

    public String addPartida(Partida p) {
        return daoPartida.addPartida(p);
    }

    public String delPartida(int id) {
        return daoPartida.delPartida(id);
    }

    public String updatePartida(Partida p) {
        return daoPartida.updatePartida(p);
    }

    public String addPersonajeToPartida(int idPartida, int idPersonaje) {
        return daoPartida.addPersonajeToPartida(idPartida, idPersonaje);
    }

    public List<Partida> getAllPartidas() {
        return daoPartida.getAllPartidas();
    }
}
