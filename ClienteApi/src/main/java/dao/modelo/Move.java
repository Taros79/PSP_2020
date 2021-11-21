package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {

    private String id;
    private String name;
    private String descripcion;

    public Move(String name, String descripcion) {
        this.name = name;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return name;
    }
}
