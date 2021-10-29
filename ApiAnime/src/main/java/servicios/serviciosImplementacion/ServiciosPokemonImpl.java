package servicios.serviciosImplementacion;

import dao.DaoPokemons;
import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
import io.vavr.control.Either;
import servicios.ServiciosPokemon;

import javax.inject.Inject;
import java.util.List;

public class ServiciosPokemonImpl implements ServiciosPokemon {
    public DaoPokemons daoPokemons;

    @Inject
    public ServiciosPokemonImpl(DaoPokemons daoPokemons) {
        this.daoPokemons = daoPokemons;
    }

    @Override
    public Either<String, Pokemon> getDatosByNombre(String id) {
        return daoPokemons.getDatosByNombre(id);
    }

    @Override
    public Either<String, List<MovesItem>> getMovimientosPorId(String id) {
        return daoPokemons.getMovimientosPorId(id);
    }

    @Override
    public Either<String, List<Pokemon>> getAllPokemon() {
        return daoPokemons.getAllPokemon();
    }

    @Override
    public Either<String, Movimiento> getDatosMovimiento(String id) {
        return daoPokemons.getDatosMovimiento(id);
    }
}
