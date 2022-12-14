package rol.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partida {

    private int id;
    private LocalDate horaInicio;
    private double duracion;
    private int numMaxParticipantes;
    private int id_Master;
}
