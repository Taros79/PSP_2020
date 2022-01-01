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
}
