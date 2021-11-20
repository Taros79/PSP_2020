package dao;

import dao.modelo.Pokemon;
import io.vavr.control.Either;

import java.util.List;

public interface DaoPokemons {

    Either<String, List<Pokemon>> getAllPokemon();

    Either<String, Pokemon> getDatosByNombre(String id);

    Either<String, Pokemon> deletePokemon(String id);
}
