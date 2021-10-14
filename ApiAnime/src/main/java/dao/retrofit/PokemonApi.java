package dao.retrofit;

import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.Pokemones;
import dao.modelo.ModPokemon.Sprites;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {

    @GET("pokemon")
    Call<Pokemones> getAnimes();

    @GET("pokemon/{characterId}")
    Call<Pokemon> getPokemonName(@Path("characterId") String id);

    @GET("pokemon/{characterId}/{sprite}")
    Call<Sprites> getPokemonSprite(@Path("characterId") String id, @Path("sprite") int id2);
}
