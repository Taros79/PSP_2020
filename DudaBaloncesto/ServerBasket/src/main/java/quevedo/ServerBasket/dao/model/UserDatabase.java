package quevedo.ServerBasket.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDatabase {

    private int idUser;
    private String password;
    private String correo;
    private String codActivacion;
    private LocalDateTime fechaLimiteActivacion;
    private boolean activo;
    private int idTipoUser;
    private String codPassw;

    public UserDatabase(int idUser, String password, String correo, String codActivacion, LocalDateTime fechaLimiteActivacion, boolean activo, int idTipoUser) {
        this.idUser = idUser;
        this.password = password;
        this.correo = correo;
        this.codActivacion = codActivacion;
        this.fechaLimiteActivacion = fechaLimiteActivacion;
        this.activo = activo;
        this.idTipoUser = idTipoUser;
    }
}
