package dao;

import dao.modeloPropio.MovesItemPrp;
import dao.modeloPropio.MovimientoPrp;
import dao.modeloPropio.PokemonPrp;
import io.vavr.control.Either;

import java.util.List;

public interface DaoPokemons {

    Either<String, PokemonPrp> getDatosByNombre(String id);

    Either<String, List<MovesItemPrp>> getMovimientosPorId(String id);

    Either<String, List<PokemonPrp>> getAllPokemon();

    Either<String, MovimientoPrp> getDatosMovimiento(String id);
}
