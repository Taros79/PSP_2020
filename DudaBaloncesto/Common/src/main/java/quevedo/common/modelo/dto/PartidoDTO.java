package quevedo.common.modelo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartidoDTO {


    private int idJornada;
    private int idEquipoLocal;
    private int idEquipoVisitante;
    private int resultadoLocal;
    private int resultadoVisitante;

}
