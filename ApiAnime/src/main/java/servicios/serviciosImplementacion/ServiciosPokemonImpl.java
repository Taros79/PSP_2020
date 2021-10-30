package servicios.serviciosImplementacion;

import dao.DaoPokemons;
import dao.modeloPropio.MovesItemPrp;
import dao.modeloPropio.MovimientoPrp;
import dao.modeloPropio.PokemonPrp;
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
    public Either<String, PokemonPrp> getDatosByNombre(String id) {
        return daoPokemons.getDatosByNombre(id);
    }

    @Override
    public Either<String, List<MovesItemPrp>> getMovimientosPorId(String id) {
        return daoPokemons.getMovimientosPorId(id);
    }

    @Override
    public Either<String, List<PokemonPrp>> getAllPokemon() {
        return daoPokemons.getAllPokemon();
    }

    @Override
    public Either<String, MovimientoPrp> getDatosMovimiento(String id) {
        return daoPokemons.getDatosMovimiento(id);
    }
}
