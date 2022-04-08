package rol.Common.modeloAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rol.Common.modelo.Estadistica;
import rol.Common.modelo.Personaje;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonajeBBDD {

    private Personaje personaje;
    private Estadistica estadistica;

}
