package notas.ServidorModule.servicios;

import jakarta.inject.Inject;
import notas.CommonModule.modeloDTO.ParteDesencriptadoDTO;
import notas.CommonModule.modeloDTO.ParteProfesorPadre;
import notas.ServidorModule.dao.DaoParte;

import java.util.List;

public class ServiciosParte {

    private final DaoParte daoParte;

    @Inject
    public ServiciosParte(DaoParte daoParte) {
        this.daoParte = daoParte;
    }

    public List<ParteDesencriptadoDTO> getPartesByUser(int idUsuario) {
        return daoParte.getPartesByUser(idUsuario);
    }

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
