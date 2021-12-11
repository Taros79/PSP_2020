package dao.modelo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {

    private String id;

    @NotEmpty
    private String name;

    private String password;


    public Usuario(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
