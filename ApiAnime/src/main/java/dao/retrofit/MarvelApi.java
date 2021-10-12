package dao.retrofit;

import dao.modelo.marvel.Marvel;
import dao.modelo.marvel.MarvelCharacters;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelApi {

    @GET("characters")
    Call<MarvelCharacters> getCharacters(@Query("nameStartsWith") String name);


    @GET("characters")
    Call<MarvelCharacters> getCharacters();

    @GET("characters")
    Single<MarvelCharacters> getCharactersRx();

    @GET("characters/{characterId}/comics")
    Call<Marvel> getComicsOfCharacter(@Path("characterId") String id);

}
