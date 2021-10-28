package dao.retrofit;

import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModObjetos.Objeto;
import dao.modelo.ModObjetos.RecursosObj;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.Recursos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {

    @GET("pokemon?limit=1118")
    Call<Recursos> getRecursosPokemon();

    @GET("pokemon/{characterId}")
    Call<Pokemon> getPokemons(@Path("characterId") String id);

    @GET("move/{characterId}")
    Call<Movimiento> getMovimientos(@Path("characterId") String id);

    @GET("item?limit=554")
    Call<RecursosObj> getRecursosItem();

    @GET("item/{id}")
    Call<Objeto> getItems(@Path("id") String id);
}
