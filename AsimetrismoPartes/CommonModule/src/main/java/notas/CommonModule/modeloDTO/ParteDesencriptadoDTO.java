package notas.CommonModule.modeloDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notas.CommonModule.modelo.Parte;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParteDesencriptadoDTO {

    private int id;
    private String descripcion;
    private int idAlumno;
    private int idTipoEstado;
}
