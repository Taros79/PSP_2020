package dao.daoImplementacion;

import dao.DaoPokemons;
import dao.modeloPropio.MovesItemPrp;
import dao.modeloPropio.MovimientoPrp;
import dao.modeloPropio.PokemonPrp;
import dao.modeloPropio.RecursosPokePrp;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoPokemonsImp implements DaoPokemons {

    PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);

    @Override
    public Either<String, PokemonPrp> getDatosByNombre(String id) {
        Either<String, PokemonPrp> resultado;

        try {
            Response<PokemonPrp> response = pokemonApi.getPokemons(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Pokemon no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, List<MovesItemPrp>> getMovimientosPorId(String id) {
        Either<String, List<MovesItemPrp>> resultado;

        try {
            Response<PokemonPrp> response = pokemonApi.getPokemons(id).execute();

            if (response.isSuccessful() && Objects.requireNonNull(response.body()).getMovimientos() != null) {
                resultado = Either.right(response.body().getMovimientos());
            } else {
                resultado = Either.left("WHOs IS TATH POKEMONE!!!");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, List<PokemonPrp>> getAllPokemon() {
        Either<String, List<PokemonPrp>> resultado;

        try {
            Response<RecursosPokePrp> response = pokemonApi.getRecursosPokemon().execute();

            if (response.isSuccessful() && Objects.requireNonNull(response.body()).getResults() != null) {
                assert response.body() != null;
                resultado = Either.right(response.body().getResults());
            } else {
                resultado = Either.left("Lista pokemones no valida");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, MovimientoPrp> getDatosMovimiento(String id) {
        Either<String, MovimientoPrp> resultado;

        try {
            Response<MovimientoPrp> response = pokemonApi.getMovimientos(id).execute();
            if (response.isSuccessful()) {
                Objects.requireNonNull(response.body());
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Movimiento no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

}
