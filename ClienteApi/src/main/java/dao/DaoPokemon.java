package dao;

import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;

import java.util.List;

public interface DaoPokemon {

    Either<String, List<Pokemon>> getAllPokemon();

    Either<String, Pokemon> getDatosByNombre(String id);

    Either<String, Pokemon> addPokemon(String id, String name, String image);

    Either<String, Pokemon> deletePokemon(String id);

    Either<String, List<Move>> getMovimientosPorId(String id);
}
