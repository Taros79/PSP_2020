package servicios;

import EE.errores.OtraException;
import dao.DaoUsuario;
import dao.modelo.Usuario;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

public class ServiciosUsuarios {

    @Inject
    private Validator validator;

    @Inject
    private DaoUsuario dao;

    public Usuario addUser(Usuario u)
    {
        final StringBuilder error = new StringBuilder();
        validator.validate(u).stream().forEach(
                testDtoConstraintViolation ->
                        error.append(testDtoConstraintViolation.getMessage()));
        if (!error.toString().isEmpty())
            throw new OtraException(error.toString());
        return dao.addUser(u);
    }

    public boolean login(String user,String pass){
        boolean loginOk=false;
        var usuario = dao.dameUsuarioPorNombre(user);
        if (usuario.isRight())
        {
            loginOk = pass.equals(usuario.get().getPassword());
        }
        else
        {
            loginOk = false;
        }
        return loginOk;

    }
    // compruebe login de un usario
}
