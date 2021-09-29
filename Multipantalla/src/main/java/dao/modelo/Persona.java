package dao.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

@Getter @Setter @ToString
public class Persona {

    private String nombre;
    private int edad;
    private boolean mujer;
    private LocalDate registro;

    public Persona(String nombre, int edad, boolean mujer, LocalDate registro) {
        this.nombre = nombre;
        this.edad = edad;
        this.mujer = mujer;
        this.registro = registro;
    }
}
