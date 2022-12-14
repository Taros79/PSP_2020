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
    private int idProfesor;
    private int idTipoEstado;
    private String firmaProfesor;
    private String firmaJefatura;
    private String firmaPadre;

    public Parte(String descripcion, int idAlumno) {
        this.descripcion = descripcion;
        this.idAlumno = idAlumno;
    }
}
