package notas.CommonModule.modeloDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notas.CommonModule.modelo.Parte;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParteProfesorPadre {

    private Parte parte;
    private int idProfesor;

}
