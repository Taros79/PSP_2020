package servicios;

import EE.errores.ApiError;
import EE.errores.OtraException;
import dao.DaoPokemon;
import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

import java.util.List;

public class ServiciosPokemon {

    @Inject
    private Validator validator;

    @Inject
    private DaoPokemon dao;

    public Either<ApiError, Pokemon> getPokemon(String id)
    {
        return dao.getPokemon(id);
    }

    public List<Pokemon> getAll()
    {
        return dao.getAll();
    }

    public boolean borrarPokemon(String id )
    {
        return dao.borrarPokemon(id);
    }

    public Pokemon addPokemon(Pokemon u)
    {
        final StringBuilder error = new StringBuilder();
        validator.validate(u).forEach(
                testDtoConstraintViolation ->
                        error.append(testDtoConstraintViolation.getMessage()));
        if (!error.toString().isEmpty())
            throw new OtraException(error.toString());
        return dao.addPokemon(u);
    }

    public Pokemon actualizarPokemon (Pokemon p) {
        return dao.actualizarPokemon(p);
    }
}
