package servicios.serviciosImplementacion;

import dao.DaoPokemon;
import dao.modelo.Move;
import dao.modelo.Pokemon;
import io.vavr.control.Either;
import servicios.ServiciosPokemon;

import javax.inject.Inject;
import java.util.List;

public class ServiciosPokemonImpl implements ServiciosPokemon {

    private DaoPokemon daoPokemons;

    @Inject
    public ServiciosPokemonImpl(DaoPokemon daoPokemons) {
        this.daoPokemons = daoPokemons;
    }

    @Override
    public Either<String, List<Pokemon>> getAllPokemon() {
        return daoPokemons.getAllPokemon();
    }

    @Override
    public Either<String, Pokemon> getDatosByNombre(String id) {
        return daoPokemons.getDatosByNombre(id);
    }

    @Override
    public Either<String, Pokemon> addPokemon(Pokemon p) {
        return daoPokemons.addPokemon(p);
    }

    @Override
    public Either<String, Pokemon> deletePokemon(String id) {
        return daoPokemons.deletePokemon(id);
    }

    @Override
    public Either<String, List<Move>> getMovimientosPorId(String id) {
        return daoPokemons.getMovimientosPorId(id);
    }

    @Override
    public Either<String, Pokemon> actualizarPokemon(Pokemon p) {
        return daoPokemons.actualizarPokemon(p);
    }
}
