package rol.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Objeto {

    private int id;
    private String nombre;
    private String descripcion;
    private String ranura;
    private int nivel;
    private int peso;
    private double precio;

    public Objeto(String nombre, String descripcion, String ranura, int nivel, int peso, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ranura = ranura;
        this.nivel = nivel;
        this.peso = peso;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Objeto: " + nombre + " [lvl " + nivel + "][" +
                ranura + "][lb " + peso + "][" + precio + " po]" +
                "\nDescripcion: " + descripcion;
    }
}
