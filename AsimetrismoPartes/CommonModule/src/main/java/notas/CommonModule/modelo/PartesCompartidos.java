package notas.CommonModule.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartesCompartidos {

    private int id;
    private int idUserACompartir;
    private int idParte;
    private String claveCifrada;

}
