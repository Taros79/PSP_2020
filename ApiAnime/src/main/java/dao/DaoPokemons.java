package dao;

import com.google.gson.Gson;
import dao.modelo.ApiError;
import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.MovesItem;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.Pokemones;
import dao.modelo.ModPokemon.ResultsItem;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.util.List;
import java.util.logging.Logger;

@Log4j2
public class DaoPokemons {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);

    public Pokemon getDatosByNombre(String id) {

        Pokemon resultado = null;

        try {
            Response<Pokemon> response = pokemonApi.getPokemonName(id).execute();

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
            Response<Pokemon> response = pokemonApi.getPokemonName(id).execute();

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

    public List<ResultsItem> getAllPokemon() {

        List<ResultsItem> resultado = null;

        try {
            Response<Pokemones> response = pokemonApi.getAnimes().execute();

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
            Response<Movimiento> response = pokemonApi.getMovimientoName(id).execute();

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
