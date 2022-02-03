package quevedo.common.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipoDTO {

    private String nombre;
    private int id;

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipoDTO)) return false;
        EquipoDTO equipoDTO = (EquipoDTO) o;
        return id == equipoDTO.id;
    }

}
