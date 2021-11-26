package dao.retrofit;

import dao.modelo.Move;
import dao.modelo.Pokemon;
import dao.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PokemonApi {

    @GET(Constantes.POKEMON_GET_ALL)
    Call<List<Pokemon>> getRecursosPokemon();

    @GET(Constantes.GET_POKEMON_ID)
    Call<Pokemon> getRecursosUnPokemon(@Path("id") String id);

    @POST(Constantes.ADD_DELETE_UPDATE_POKEMON)
    Call<Pokemon> addPokemon(@Body Pokemon p);

    @DELETE(Constantes.ADD_DELETE_UPDATE_POKEMON)
    Call<Pokemon> deletePokemon(@Query("id") String id);

    @PUT(Constantes.ADD_DELETE_UPDATE_POKEMON)
    Call<Pokemon> actualizarPokemon(@Body Pokemon p);



    @GET(Constantes.MOVE_GET_ALL)
    Call<List<Move>> getRecursosMove();

    @GET(Constantes.GET_MOVE_ID)
    Call<Move> getUnMovimiento(@Path("id") String id);

    @DELETE(Constantes.ADD_DELETE_UPDATE_MOVE)
    Call<Move> deleteMove(@Query("id") String id);

    @POST(Constantes.ADD_DELETE_UPDATE_MOVE)
    Call<Move> addMove(@Body Move move);

    @PUT(Constantes.ADD_DELETE_UPDATE_MOVE)
    Call<Move> actualizarMove(@Body Move m);

}
