package servicios;

import EE.errores.ApiError;
import EE.errores.OtraException;
import dao.DaoUsuario;
import dao.modelo.Usuario;
import io.vavr.control.Either;

import jakarta.inject.Inject;
import jakarta.validation.Validator;
import java.util.List;

public class ServiciosUsuarios {

    @Inject
    private Validator validator;

    @Inject
    private DaoUsuario dao;

    public Either<ApiError, Usuario> dameUno(String id)
    {
        return dao.dameUno(id);
    }

    public List<Usuario> dameTodos()
    {
        return dao.dameTodos();
    }

    public boolean borrar(String id )
    {
        return dao.borrar(id);
    }

    public  Usuario addUser(Usuario u)
    {
        final StringBuilder error = new StringBuilder();
        validator.validate(u).stream().forEach(
                testDtoConstraintViolation ->
                        error.append(testDtoConstraintViolation.getMessage()));
        if (!error.toString().isEmpty())
            throw new OtraException(error.toString());
        return dao.addUser(u);
    }
}
