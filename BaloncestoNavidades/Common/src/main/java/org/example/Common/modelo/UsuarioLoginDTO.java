package org.example.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginDTO {

    private String username;
    private String hashedPassword;
    private int isActivo;
    private int tipoUsuario;
}
