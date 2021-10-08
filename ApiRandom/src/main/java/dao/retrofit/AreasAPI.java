package dao.retrofit;


import dao.modelo.Area;
import dao.modelo.AreasRequest;
import dao.modelo.CompetitionsRequest;
import dao.modelo.Usuario;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.*;


public interface AreasAPI {

  @GET("areas/")
  Single<AreasRequest> loadAreas();

  @GET("areas/{id}")
  Call<Area> loadOneArea(@Path("id") int id);

  @GET("competitions/")
  Call<CompetitionsRequest> loadCompetitions(@Query("areas") long areas);

  @POST("api/users")
  Observable<Usuario> addUsuario(@Body Usuario usuario);

  @DELETE("api/users")
  Call<Usuario> delUsuario(@Query("usuario") Usuario usuario);




//  Call<TeamsRequest> loadTeams(@Path("id") long id,@Query("season") String season);

}
