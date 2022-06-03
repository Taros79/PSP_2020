package notas.CommonModule.modeloDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParteDesencriptadoDTO {

    private int id;
    private String descripcion;
    private String alumno;
    private int idTipoEstado;
    private String tipoEstado;


    public void setTipoEstado(int idTipoEstado) {
        switch (idTipoEstado) {
            case 1:
                this.tipoEstado = "creado";
                break;
            case 2:
                this.tipoEstado = "confirmado";
                break;
            case 3:
                this.tipoEstado = "rechazado";
                break;
            case 4:
                this.tipoEstado = "visto";
                break;
            default:
                this.tipoEstado = "";
                break;
        }
    }

    public ParteDesencriptadoDTO(int id, String descripcion, String alumno, int idTipoEstado) {
        this.id = id;
        this.descripcion = descripcion;
        this.alumno = alumno;
        this.idTipoEstado = idTipoEstado;
    }

    @Override
    public String toString() {
        if(tipoEstado == null) {
           return "Alumno: " + alumno +
                   "\nMotivo Parte: " + descripcion;
        }else{
            return "Alumno: " + alumno +
                    "\nMotivo Parte: " + descripcion +
                    "\nEstado: " + tipoEstado;
        }
    }
}
