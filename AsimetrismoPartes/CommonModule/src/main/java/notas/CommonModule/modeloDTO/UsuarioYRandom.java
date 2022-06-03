package notas.CommonModule.modeloDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notas.CommonModule.modelo.Parte;
import notas.CommonModule.modelo.Usuario;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioYRandom {

    private Usuario usuario;
    private String randomDesencriptada;

}
