package rol.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private int id;
    private String correo;
    private String contraseña;
    private int tipo_Usuario;
    private int baneado;

    public Usuario(String correo, String contraseña, int tipo_Usuario, int baneado) {
        this.correo = correo;
        this.contraseña = contraseña;
        this.tipo_Usuario = tipo_Usuario;
        this.baneado = baneado;
    }

    public Usuario(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }
}
