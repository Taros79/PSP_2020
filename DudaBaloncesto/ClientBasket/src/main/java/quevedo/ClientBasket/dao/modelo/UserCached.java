package quevedo.ClientBasket.dao.modelo;


import lombok.Data;

import javax.inject.Singleton;

@Singleton
@Data
public class UserCached {

    private String user;
    private String pass;
    private String jwt;

}
