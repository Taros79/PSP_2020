package dao.retrofit;

import dao.modelo.ModPokemon.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AnimeApi {

    @GET("pokemon/2/")
    Call<Pokemon> getAnimes();

}
