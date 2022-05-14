package notas.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnosPadre {

    private int id;
    private int idAlumno;
    private int idUsuario;

}
