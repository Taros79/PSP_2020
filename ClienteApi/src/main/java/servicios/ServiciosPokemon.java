package servicios;

import dao.modelo.Pokemon;
import io.vavr.control.Either;

public interface ServiciosPokemon {
    Either<String, Pokemon> getDatosByNombre(String id);

    Either<String, Pokemon> deletePokemon(String id);
}
