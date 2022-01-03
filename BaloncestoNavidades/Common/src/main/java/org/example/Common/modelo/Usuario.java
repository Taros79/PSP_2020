package org.example.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String correo;
    private String username;
    private String hashedPassword;
    private String codActivacion;
    private int isActivo;
    private LocalDateTime fechaAlta;
    private int tipoUsuario;

    public Usuario(String correo, String username, String hashedPassword, String codActivacion, int isActivo, LocalDateTime fechaAlta, int tipoUsuario) {
        this.correo = correo;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.codActivacion = codActivacion;
        this.isActivo = isActivo;
        this.fechaAlta = fechaAlta;
        this.tipoUsuario = tipoUsuario;
    }
}
