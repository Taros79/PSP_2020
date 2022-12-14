package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecretoCompartido {
    private int id;
    private String userACompartir;
    private String claveCifrada;
    private int idSecreto;

    public SecretoCompartido(String userACompartir, String claveCifrada, int idSecreto) {
        this.userACompartir = userACompartir;
        this.claveCifrada = claveCifrada;
        this.idSecreto = idSecreto;
    }
}
