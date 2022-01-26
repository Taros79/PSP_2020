package org.example.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partido {
    private int idPartido;
    private int idJornada;
    private int idLocal;
    private int idVisitante;
    private int resultadoLocal;
    private int resultadoVisitante;

    public Partido(int idJornada, int idLocal, int idVisitante, int resultadoLocal, int resultadoVisitante) {
        this.idJornada = idJornada;
        this.idLocal = idLocal;
        this.idVisitante = idVisitante;
        this.resultadoLocal = resultadoLocal;
        this.resultadoVisitante = resultadoVisitante;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "idJornada=" + idJornada +
                ", idLocal=" + idLocal +
                ", idVisitante=" + idVisitante +
                ", resultadoLocal=" + resultadoLocal +
                ", resultadoVisitante=" + resultadoVisitante +
                '}';
    }
}
