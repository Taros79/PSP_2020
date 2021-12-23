package org.example.Common.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioLoginDTO {

    private String id;
    private String name;
    private String password;
}
