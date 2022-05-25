package notas.ServidorModule.servicios;

import jakarta.inject.Inject;
import notas.CommonModule.modelo.Parte;
import notas.CommonModule.modelo.ParteProfesorPadre;
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

    public String addParte(ParteProfesorPadre parte) {
        return daoParte.addParte(parte);
    }

    public String addParteCompartido(int idUsuario, int idParte) {
        return daoParte.addParteCompartido(idUsuario, idParte);
    }

    public String updateParte(int idParte, int estado) {
        return daoParte.updateParte(idParte, estado);
    }
}
