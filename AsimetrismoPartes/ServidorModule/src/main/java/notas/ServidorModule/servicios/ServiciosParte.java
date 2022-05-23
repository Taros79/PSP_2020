package notas.ServidorModule.servicios;

import jakarta.inject.Inject;
import notas.CommonModule.modelo.Parte;
import notas.ServidorModule.dao.DaoParte;

public class ServiciosParte {

    private final DaoParte daoParte;

    @Inject
    public ServiciosParte(DaoParte daoParte) {
        this.daoParte = daoParte;
    }

 /*   public List<Parte> getAllPartes() {
        return daoParte.getAllPartes();
    }

    public List<Parte> getPartesByUser(int idPadre) {
        return daoParte.getPartesByUser(idPadre);
    }*/

    public Integer addParte(Parte parte) {
        return daoParte.addParte(parte);
    }

    public String addParteCompartido(String username, int idParte) {
        return daoParte.addParteCompartido(username, idParte);
    }

    public String updateParte(int idParte, int estado) {
        return daoParte.updateParte(idParte, estado);
    }
}
