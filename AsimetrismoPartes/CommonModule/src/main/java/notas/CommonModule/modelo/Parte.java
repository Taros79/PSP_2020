package notas.CommonModule.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parte {

    private int id;
    private String descripcion;
    private int idAlumno;
    private int idTipoEstado;

    public Parte(String descripcion, int idAlumno) {
        this.descripcion = descripcion;
        this.idAlumno = idAlumno;
    }
}
