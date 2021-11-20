package dao;

import dao.modelo.Pokemon;
import io.vavr.control.Either;

public interface DaoPokemons {

    Either<String, Pokemon> getDatosByNombre(String id);

    Either<String, Pokemon> deletePokemon(String id);
}
