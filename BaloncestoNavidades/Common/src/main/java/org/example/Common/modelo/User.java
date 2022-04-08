package org.example.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String correo;
    private String hashedPassword;
    private int tipoUsuario;

    public User(String correo, String hashedPassword) {
        this.correo = correo;
        this.hashedPassword = hashedPassword;
    }
}
