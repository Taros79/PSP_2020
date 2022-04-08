package rol.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hechizo {

    private int id;
    private String nombre;
    private String descripcion;
    private int nivel;

    public Hechizo(String nombre, String descripcion, int nivel) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Hechizo: " + nombre + " [lvl " + nivel + "]" + "\nDescripcion: " + descripcion;
    }
}
