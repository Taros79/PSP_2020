package quevedo.common.modelo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class JornadaDTO {

    private LocalDate fecha;
    private int id;

    @Override
    public String toString() {
        return fecha.toString();
    }
}
