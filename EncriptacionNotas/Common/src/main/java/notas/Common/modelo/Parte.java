package notas.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parte {

    private int id;
    private String descripcion;
    private String idAlumno;
    private String idTipoEstado;

}
