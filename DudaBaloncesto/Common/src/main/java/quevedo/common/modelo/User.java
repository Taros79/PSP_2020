package quevedo.common.modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String correo;
    private String password;
    private int tipoUser;

    public User(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }
}
