package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {

    private String id;
    private String name;
    private String descripcion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return Objects.equals(getId(), move.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
