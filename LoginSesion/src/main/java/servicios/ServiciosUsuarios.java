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
    // compruebe login de un usario
}
