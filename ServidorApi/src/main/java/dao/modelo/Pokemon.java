package dao.modelo;

import jakarta.validation.constraints.NotEmpty;
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

}
