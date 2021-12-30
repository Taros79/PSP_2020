package org.example.ModuloServidor.dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class    Usuario {
    private String correo;
    private String pass;
    private String codActivacion;
    private int idUsuario;
    private LocalDateTime fechaAlta;
    private int idTipoUsuario;
    private int confirmacion;
}
