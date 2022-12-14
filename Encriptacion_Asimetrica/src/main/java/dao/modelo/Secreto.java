package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Secreto {
    private int id;
    private String nombre;
    private String secretoEncriptado;

    public Secreto(String nombre, String secretoEncriptado) {
        this.nombre = nombre;
        this.secretoEncriptado = secretoEncriptado;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
