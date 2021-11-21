package dao.retrofit;

import dao.modelo.Move;
import dao.modelo.Pokemon;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PokemonApi {

    @GET("pokemon/getAll")
    Call<List<Pokemon>> getRecursosPokemon();

    @GET("pokemon/{id}")
    Call<Pokemon> getRecursosUnPokemon(@Path("id") String id);

    @POST("pokemon/")
    Call<Pokemon> addPokemon(@Body Pokemon p);

    @DELETE("pokemon/")
    Call<Pokemon> deletePokemon(@Query("id") String id);


    @GET("move/getAll")
    Call<List<Move>> getRecursosMove();

    @GET("move/{id}")
    Call<Move> getUnMovimiento(@Path("id") String id);

   /* @POST("move/")
    Call<Move> addMove(@Query("id") String id,
                             @Query("name") String name,
                             @Query("descripcion") String descripcion);*/

    @DELETE("move/")
    Call<Move> deleteMove(@Query("id") String id);

    @POST("move/")
    Call<Move> addMove(@Body Move move);

}
