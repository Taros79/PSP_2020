package dao.retrofit;

import dao.modelo.Pokemon;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonApi {

    @GET("pokemon?limit=400")
    Call<Pokemon> getRecursosPokemon();

    @GET("pokemon/{id}")
    Call<Pokemon> getRecursosUnPokemon(@Path("id") String id);

    @DELETE("pokemon/")
    Call<Pokemon> deletePokemon(@Query("id") String id);
}
