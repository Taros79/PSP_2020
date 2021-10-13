package dao.retrofit;

import dao.modelo.ModPokemon.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonApi {

    @GET("pokemon/2")
    Call<Pokemon> getAnimes();

    @GET("pokemon")
    Call<Pokemon> getPokemonName();

}
