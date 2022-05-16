package notas.Servidor.servicios;

import jakarta.inject.Inject;
import notas.Common.modelo.Parte;
import notas.Servidor.dao.DaoParte;

import java.util.List;

public class ServiciosParte {

    private final DaoParte daoParte;

    @Inject
    public ServiciosParte(DaoParte daoParte) {
        this.daoParte = daoParte;
    }

    public List<Parte> getAllPartes() {
        return daoParte.getAllPartes();
    }

    public String addParte(Parte parte) {
        return daoParte.addParte(parte);
    }
}
