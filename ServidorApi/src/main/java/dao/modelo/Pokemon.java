package dao.modelo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {

    private String id;

    private String name;
    private String image;
    private LocalDateTime fechaDescubrimiento;
    private List<Move> moves;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pokemon)) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(getId(), pokemon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
