package notas.CommonModule.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private int id;
    private String nombre;
    private String pass;
    private int idTipoUsuario;

    public Usuario(String nombre, String pass, int idTipoUsuario) {
        this.nombre = nombre;
        this.pass = pass;
        this.idTipoUsuario = idTipoUsuario;
    }
}
