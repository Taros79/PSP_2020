package dao.retrofit;

import dao.modelo.Pokemoneh.DataFlavors;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AnimeApi {

    @GET("berry/2")
    Call<DataFlavors> getAnimes();

}
