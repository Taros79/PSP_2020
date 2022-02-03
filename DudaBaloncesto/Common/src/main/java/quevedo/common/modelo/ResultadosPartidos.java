package quevedo.common.modelo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ResultadosPartidos {

    private String equipoLocal;
    private String equipoVisitante;
    private LocalDate jornada;
    private int puntosLocal;
    private int puntosVisitante;

    @Override
    public String toString() {
        return "Jornada : " + jornada+ "\n" +
                equipoLocal + " " + puntosLocal + " - " + puntosVisitante + " " + equipoVisitante;
    }
}
