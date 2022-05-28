package notas.ClienteModule.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modelo.Parte;
import notas.CommonModule.modeloDTO.ParteDesencriptadoDTO;
import notas.CommonModule.modeloDTO.ParteProfesorPadre;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiParte {

    @GET(ConstantesRest.PATH_PARTES)
    Single<List<ParteDesencriptadoDTO>> getPartesByUser(@Query("idUsuario") int idUsuario);

    @POST(ConstantesRest.PATH_PARTES)
    Single<String> addParte(@Body ParteProfesorPadre p);

    @POST(ConstantesRest.PATH_PARTES)
    Call<Integer> addParteTO(@Body Parte p);

    @POST(ConstantesRest.PATH_PARTES + ConstantesRest.PATH_ADD_PARTE_COMPARTIDO)
    Single<String> addParteCompartido(@Query("idUsuario") int idUsuario, @Query("idParte") int idParte);

    @PUT(ConstantesRest.PATH_PARTES)
    Single<String> updateParte(@Query("idParte") int idParte, @Query("estado") int estado);
}
