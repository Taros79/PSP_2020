package dao.retrofit;

import dao.modeloPropio.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {

    @GET("pokemon?limit=1118")
    Call<RecursosPokePrp> getRecursosPokemon();

    @GET("pokemon/{characterId}")
    Call<PokemonPrp> getPokemons(@Path("characterId") String id);

    @GET("move/{characterId}")
    Call<MovimientoPrp> getMovimientos(@Path("characterId") String id);

    @GET("item?limit=554")
    Call<RecursosObjPrp> getRecursosItem();

    @GET("item/{id}")
    Call<ObjetoPrp> getItems(@Path("id") String id);
}
