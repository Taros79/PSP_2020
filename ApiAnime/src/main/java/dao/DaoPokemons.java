package dao;

import com.google.gson.Gson;
import dao.modelo.ApiError;
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
        Either<String, Pokemon> resultado = null;
        try {
            Response<Pokemon> response = pokemonApi.getPokemons(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("usuario no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    public Either<String, List<MovesItem>> getMovimientosPorId(String id) {
        Either<String, List<MovesItem>> resultado = null;
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

    public List<Pokemon> getAllPokemon() {

        List<Pokemon> resultado = null;

        try {
            Response<Recursos> response = pokemonApi.getRecursosPokemon().execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                resultado = response.body().getResults();
            } else {
                Gson g = new Gson();
                ApiError apierror = g.fromJson(response.errorBody().string(), ApiError.class);

            }
        } catch (Exception e) {
            log.error(e.getMessage());
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
            } else {
                Gson g = new Gson();
                ApiError apierror = g.fromJson(response.errorBody().string(), ApiError.class);

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultado;
    }

}
