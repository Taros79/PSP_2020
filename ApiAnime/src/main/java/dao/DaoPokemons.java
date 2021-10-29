package dao;

import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
import io.vavr.control.Either;

import java.util.List;

public interface DaoPokemons {

    Either<String, Pokemon> getDatosByNombre(String id);

    Either<String, List<MovesItem>> getMovimientosPorId(String id);

    Either<String, List<Pokemon>> getAllPokemon();

    Either<String, Movimiento> getDatosMovimiento(String id);
}
