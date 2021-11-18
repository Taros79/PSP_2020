package dao.modelo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private String id;

    @NotEmpty
    private String name;

    private LocalDateTime primerSopapoDeMiMadre;

}
