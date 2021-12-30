package org.example.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private String correo;
    private String pass;
    private String codActivacion;
    private int idUsuario;
    private LocalDateTime fechaAlta;
    private int idTipoUsuario;
    private int confirmacion;

    public Usuario(String correo, String pass, String codActivacion, LocalDateTime fechaAlta, int idTipoUsuario, int confirmacion) {
        this.correo = correo;
        this.pass = pass;
        this.codActivacion = codActivacion;
        this.fechaAlta = fechaAlta;
        this.idTipoUsuario = idTipoUsuario;
        this.confirmacion = confirmacion;
    }
}
