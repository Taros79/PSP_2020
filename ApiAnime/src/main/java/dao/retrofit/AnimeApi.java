package dao.retrofit;

import dao.modelo.Pokemoneh.DataFlavors;
import dao.modelo.sfdf.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AnimeApi {

    @GET("pokemon/2/")
    Call<Pokemon> getAnimes();

}
