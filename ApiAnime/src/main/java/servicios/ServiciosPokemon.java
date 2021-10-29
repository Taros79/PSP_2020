package servicios;

import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
import io.vavr.control.Either;

import java.util.List;

public interface ServiciosPokemon {
    Either<String, Pokemon> getDatosByNombre(String id);

    Either<String, List<MovesItem>> getMovimientosPorId(String id);

    Either<String, List<Pokemon>> getAllPokemon();

    Movimiento getDatosMovimiento(String id);
}
