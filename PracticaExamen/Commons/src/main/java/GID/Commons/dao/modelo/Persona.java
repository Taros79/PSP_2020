package GID.Commons.dao.modelo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Persona {
    public String id;
    public String nombre;
    public String estadoCivil;
    public String sexo;
    public LocalDateTime fechaNacimiento;
    public String lugarNacimiento;
    public String idPersonaCasada;
    public List<Persona> hijos;
}
