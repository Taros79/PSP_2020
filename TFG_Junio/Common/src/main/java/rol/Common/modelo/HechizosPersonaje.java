package rol.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HechizosPersonaje {

    private int id;
    private int id_Hechizo;
    private int id_Personaje;

}
