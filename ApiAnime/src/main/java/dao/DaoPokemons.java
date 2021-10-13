package dao;

import com.google.gson.Gson;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.Pokemoneh.ApiError;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.util.logging.Logger;

@Log4j2
public class DaoPokemons {

    private Logger logger = Logger.getLogger(this.getClass().getName());


    public String getAll() {

        PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);
        String resultado = null;

        try {
            Response<Pokemon> response = pokemonApi.getAnimes().execute();

            if (response.isSuccessful()) {
                resultado = response.body().getNombre();
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
