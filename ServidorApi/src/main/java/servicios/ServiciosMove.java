package servicios;

import EE.errores.ApiError;
import EE.errores.OtraException;
import dao.DaoMove;
import dao.modelo.Move;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

import java.util.List;

public class ServiciosMove {

    @Inject
    private Validator validator;

    @Inject
    private DaoMove dao;

    public Either<ApiError, Move> getMove(String id)
    {
        return dao.getMove(id);
    }

    public List<Move> getAll()
    {
        return dao.getAll();
    }

    public boolean borrarMove(String id )
    {
        return dao.borrarMove(id);
    }

    public Move addMove(Move u)
    {
        final StringBuilder error = new StringBuilder();
        validator.validate(u).stream().forEach(
                testDtoConstraintViolation ->
                        error.append(testDtoConstraintViolation.getMessage()));
        if (!error.toString().isEmpty())
            throw new OtraException(error.toString());
        return dao.addMove(u);
    }

    public Move actualizarMove (Move m) {
        return dao.actualizarMove(m);
    }
}
