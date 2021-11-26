package GID.Commons.dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Persona {
    public String id;
    public String nombre;
    public String estadoCivil;
    public String sexo;
    public String lugarNacimiento;
    public String idPersonaCasada;
    public LocalDateTime fechaNacimiento;
    public List<Persona> hijos;


}
