package dao;

import dao.modelo.Usuario;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoUsuario {

    private static List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("nombre","pass"));
    }


    public DaoUsuario() {
    }

    public Usuario addUser(Usuario user)
    {
        usuarios.add(user);
        return user;
    }
}
