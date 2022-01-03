package org.example.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRegistro {
    private String correo;
    private String username;
    private String hashedPassword;
    private int tipoUsuario;
}
