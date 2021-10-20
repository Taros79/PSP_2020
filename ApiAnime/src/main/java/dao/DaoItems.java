package dao;

import com.google.gson.Gson;
import dao.modelo.ApiError;
import dao.modelo.ModObjetos.Objeto;
import dao.modelo.ModObjetos.RecursosObj;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.util.List;

@Log4j2
public class DaoItems {

    PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);

    public List<Objeto> getAllItems() {

        List<Objeto> resultado = null;

        try {
            Response<RecursosObj> response = pokemonApi.getRecursosItem().execute();

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

    public Objeto getItemsByNombre(String id) {
        Objeto resultado = null;

        try {
            Response<Objeto> response = pokemonApi.getItems(id).execute();

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

}
