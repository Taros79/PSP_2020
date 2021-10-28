package servicios;

import dao.DaoPokemons;
import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
import io.vavr.control.Either;

import java.util.List;

public class ServiciosPokemon {
    DaoPokemons daoPokemons = new DaoPokemons();

    public Either<String, Pokemon> getDatosByNombre(String id) {
        return daoPokemons.getDatosByNombre(id);
    }

    public Either<String, List<MovesItem>> getMovimientosPorId(String id) {
        return daoPokemons.getMovimientosPorId(id);
    }

    public Either<String, List<Pokemon>> getAllPokemon() {
        return daoPokemons.getAllPokemon();
    }

    public Movimiento getDatosMovimiento(String id) {
        return daoPokemons.getDatosMovimiento(id);
    }
}
