package dao;

import com.google.gson.Gson;
import dao.modelo.ApiError;
import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.Recursos;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.util.List;

@Log4j2
public class DaoPokemons {

    PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);

    public Pokemon getDatosByNombre(String id) {

        Pokemon resultado = null;

        try {
            Response<Pokemon> response = pokemonApi.getPokemons(id).execute();

            if (response.isSuccessful()) {
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

    public List<MovesItem> getMovimientosPorId(String id) {
        List<MovesItem> resultado = null;
        try {
            Response<Pokemon> response = pokemonApi.getPokemons(id).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                resultado = response.body().getMovimientos();
            } else {
                Gson g = new Gson();
                ApiError apierror = g.fromJson(response.errorBody().string(), ApiError.class);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
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
