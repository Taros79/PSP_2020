package org.example.ModuloServidor.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDatabase {

    private int id;
    private String correo;
    private String username;
    private String hashedPassword;
    private String codActivacion;
    private boolean isActivo;
    private LocalDateTime fechaAlta;
    private int tipoUsuario;

    public UserDatabase(int id, String hashedPassword, String username, String correo, String codActivacion, LocalDateTime fechaAlta, boolean isActivo, int tipoUsuario) {
        this.id = id;
        this.hashedPassword = hashedPassword;
        this.correo = correo;
        this.username = username;
        this.codActivacion = codActivacion;
        this.fechaAlta = fechaAlta;
        this.isActivo = isActivo;
        this.tipoUsuario = tipoUsuario;
    }
}
