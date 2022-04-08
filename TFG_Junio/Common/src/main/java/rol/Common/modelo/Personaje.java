package rol.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personaje {

    private int id;
    private String nombre;
    private String raza;
    private String clase;
    private String alineamiento;
    private int nivel;
    private int experiencia;
    private int id_Estadistica;

    public Personaje(String nombre, String raza, String clase, String alineamiento, int nivel, int experiencia) {
        this.nombre = nombre;
        this.raza = raza;
        this.clase = clase;
        this.alineamiento = alineamiento;
        this.nivel = nivel;
        this.experiencia = experiencia;
    }
}
