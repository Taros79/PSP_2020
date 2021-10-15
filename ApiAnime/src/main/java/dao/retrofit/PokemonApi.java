package dao.retrofit;

import dao.modelo.ModMovimientos.Movimiento;
import dao.modelo.ModPokemon.Pokemon;
import dao.modelo.ModPokemon.Pokemones;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {

    @GET("pokemon?limit=1118")
    Call<Pokemones> getAnimes();

    @GET("pokemon/{characterId}")
    Call<Pokemon> getPokemonName(@Path("characterId") String id);

    @GET("pokemon/{characterId}")
    Call<Pokemon> getPokemonUbicacion(@Path("characterId") String id);

    @GET("move/{characterId}")
    Call<Movimiento> getMovimientoName(@Path("characterId") String id);
}
