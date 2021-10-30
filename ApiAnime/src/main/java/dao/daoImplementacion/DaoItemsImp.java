package dao.daoImplementacion;

import dao.DaoItems;
import dao.modeloPropio.ObjetoPrp;
import dao.modeloPropio.RecursosObjPrp;
import dao.retrofit.PokemonApi;
import dao.utils.ConfigurationSingleton_OkHttpClient;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoItemsImp implements DaoItems {

    PokemonApi pokemonApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PokemonApi.class);

    @Override
    public Either<String, List<ObjetoPrp>> getAllItems() {
        Either<String, List<ObjetoPrp>> resultado;

        try {
            Response<RecursosObjPrp> response = pokemonApi.getRecursosItem().execute();

            if (response.isSuccessful() && Objects.requireNonNull(response.body()).getResults() != null) {
                assert response.body() != null;
                resultado = Either.right(response.body().getResults());
            } else {
                resultado = Either.left("Lista no valida");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

    @Override
    public Either<String, ObjetoPrp> getItemsByNombre(String id) {
        Either<String, ObjetoPrp> resultado;

        try {
            Response<ObjetoPrp> response = pokemonApi.getItems(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left("Item no valido");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultado = Either.left("Error bbdd");
        }
        return resultado;
    }

}
