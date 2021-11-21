package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {

    private String id;

    private String name;
    private String image;
    private LocalDateTime fechaDescubrimiento;
    private List<Move> moves;

    public Pokemon(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }
}
