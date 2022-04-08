package rol.Servidor.servicios;

import jakarta.inject.Inject;
import rol.Common.modelo.Estadistica;
import rol.Servidor.dao.DaoEstadistica;

public class ServiciosEstadistica {

    private final DaoEstadistica daoEstadistica;

    @Inject
    public ServiciosEstadistica(DaoEstadistica daoEstadistica) {
        this.daoEstadistica = daoEstadistica;
    }

    public Estadistica getEstadisticaById(int id) {
        return daoEstadistica.getEstadisticaById(id);
    }

    public String addEstadistica(Estadistica es) {
        return daoEstadistica.addEstadistica(es);
    }

    public String delEstadistica(int id) {
        return daoEstadistica.delEstadistica(id);
    }

    public String updateEstadistica(Estadistica es) {
        return daoEstadistica.updateEstadistica(es);
    }


}
