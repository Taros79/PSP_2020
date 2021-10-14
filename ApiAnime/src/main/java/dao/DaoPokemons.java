package dao;

import com.google.gson.Gson;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.Pokemones;
import dao.modelo.ModPokemon.ResultsItem;
import dao.modelo.ModPokemon.Sprites;
import dao.modelo.Pokemoneh.ApiError;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.util.List;
import java.util.logging.Logger;

@Log4j2
public class DaoPokemons {

    private Logger logger = Logger.getLogger(this.getClass().getName());


    public Pokemon getDatosByNombre(String id) {

        PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);
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

    public String getSpriteId(String id, int id2) {

        PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);
        String resultado = null;

        try {
            Response<Sprites> response = pokemonApi.getPokemonSprite(id, id2).execute();

            if (response.isSuccessful()) {
                resultado = response.body().getFrontDefault();
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

        PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);
        List<ResultsItem> resultado = null;

        try {
            Response<Pokemones> response = pokemonApi.getAnimes().execute();

            if (response.isSuccessful()) {
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

}
