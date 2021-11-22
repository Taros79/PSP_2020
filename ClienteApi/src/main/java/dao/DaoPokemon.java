package dao;

import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;

import java.util.List;

public interface DaoPokemon {

    Either<String, List<Pokemon>> getAllPokemon();

    Either<String, Pokemon> getDatosByNombre(String id);

    Either<String, Pokemon> addPokemon(Pokemon p);

    Either<String, Pokemon> deletePokemon(String id);

    Either<String, List<Move>> getMovimientosPorId(String id);

    Either<String, Pokemon> actualizarPokemon(Pokemon p);
}
