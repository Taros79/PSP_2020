package dao.retrofit;

import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.Pokemones;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {

    @GET("pokemon")
    Call<Pokemones> getAnimes();

    @GET("pokemon/{characterId}")
    Call<Pokemon> getPokemonName(@Path("characterId") String id);

}
