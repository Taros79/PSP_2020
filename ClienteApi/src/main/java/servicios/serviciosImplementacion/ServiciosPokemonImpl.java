package servicios.serviciosImplementacion;

import dao.DaoPokemons;
import dao.modelo.Pokemon;
import io.vavr.control.Either;
import servicios.ServiciosPokemon;

import javax.inject.Inject;

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
    public Either<String, Pokemon> deletePokemon(String id) {
        return daoPokemons.deletePokemon(id);
    }
}
