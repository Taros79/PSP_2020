package org.example.Common.modelo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jornada {
    private int id;
    private LocalDateTime fecha;

    public Jornada(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
