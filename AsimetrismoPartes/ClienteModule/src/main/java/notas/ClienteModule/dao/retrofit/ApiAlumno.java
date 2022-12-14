package notas.ClienteModule.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modelo.Alumno;
import retrofit2.http.GET;

import java.util.List;

public interface ApiAlumno {

    @GET(ConstantesRest.PATH_ALUMNOS)
    Single<List<Alumno>> getAllAlumnos();
}
