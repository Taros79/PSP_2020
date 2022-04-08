package rol.Common.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosPartida {

    private int id;
    private int id_Partida;
    private int id_Usuario;

}
