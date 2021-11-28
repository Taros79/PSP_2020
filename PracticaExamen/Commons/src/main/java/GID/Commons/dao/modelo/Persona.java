package GID.Commons.dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    public String id;
    public String nombre;
    public String estadoCivil;
    public String sexo;
    public String lugarNacimiento;
    public String idPersonaCasada;
    public LocalDateTime fechaNacimiento;
    public List<Persona> hijos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona persona = (Persona) o;
        return Objects.equals(getId(), persona.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Persona(String id, String nombre, String estadoCivil, String sexo, LocalDateTime fechaNacimiento, String lugarNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.lugarNacimiento = lugarNacimiento;
    }

    public Persona(String nombre, String estadoCivil, String sexo, LocalDateTime fechaNacimiento, String lugarNacimiento, String idPersonaCasada, List<Persona> hijos) {
        this.nombre = nombre;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.lugarNacimiento = lugarNacimiento;
        this.idPersonaCasada = idPersonaCasada;
        this.hijos = hijos;
    }
}
