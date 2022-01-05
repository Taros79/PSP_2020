package org.example.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRegistro {
    private String correo;
    private String username;
    private String hashedPassword;
    private String codActivacion;
    private int isActivo;
    private LocalDateTime fechaAlta;
    private int tipoUsuario;
}
