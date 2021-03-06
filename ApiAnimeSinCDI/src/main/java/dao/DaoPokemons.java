package dao;

import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.Recursos;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoPokemons {

    PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);

    public Either<String, Pokemon> getDatosByNombre(String id) {
        Either<String, Pokemon> resultado;

        try {
            Response<Pokemon> response = pokemonApi.getPokemons(id).execute();

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

    public Either<String, List<MovesItem>> getMovimientosPorId(String id) {
        Either<String, List<MovesItem>> resultado;

        try {
            Response<Pokemon> response = pokemonApi.getPokemons(id).execute();

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

    public Either<String, List<Pokemon>> getAllPokemon() {
        Either<String, List<Pokemon>> resultado;

        try {
            Response<Recursos> response = pokemonApi.getRecursosPokemon().execute();

            if (response.isSuccessful() && Objects.requireNonNull(response.body()).getResults() != null) {
                assert response.body() != null;
                resultado = Either.right(response.body().getResults());
            } else {
                resultado = Either.left("Pokemon no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    public Movimiento getDatosMovimiento(String id) {
        Movimiento resultado = null;

        try {
            Response<Movimiento> response = pokemonApi.getMovimientos(id).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                resultado = response.body();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return resultado;
    }

}
