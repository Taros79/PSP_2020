package rol.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dote {

    private int id;
    private String nombre;
    private String descripcion;

    public Dote(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Dote: " + nombre + "\nDescripcion: " + descripcion;
    }
}
