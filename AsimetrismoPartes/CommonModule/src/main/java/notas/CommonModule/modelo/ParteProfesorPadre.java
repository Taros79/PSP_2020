package notas.CommonModule.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParteProfesorPadre {

    private Parte parte;
    private int idProfesor;

}
