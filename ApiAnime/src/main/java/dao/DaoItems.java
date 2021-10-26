package dao;

import dao.modelo.ModObjetos.Objeto;
import dao.modelo.ModObjetos.RecursosObj;
import dao.modelo.ModPokemon.Pokemon;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoItems {

    PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);

    public Either<String, List<Objeto>> getAllItems() {
        Either<String, List<Objeto>> resultado;

        try {
            Response<RecursosObj> response = pokemonApi.getRecursosItem().execute();

            if (response.isSuccessful() && Objects.requireNonNull(response.body()).getResults() != null) {
                assert response.body() != null;
                resultado =  Either.right(response.body().getResults());
            } else {
                resultado = Either.left("Item no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultado = Either.left("Error bbdd");
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


            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultado;
    }

}
