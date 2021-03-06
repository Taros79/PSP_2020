package org.example.Common.modelo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipo {
    private int idEquipo;
    private String nombre;

    public Equipo(String nombre) {
        this.nombre = nombre;
    }
}
