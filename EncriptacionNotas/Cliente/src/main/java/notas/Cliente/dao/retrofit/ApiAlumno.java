package notas.Cliente.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import notas.Common.constantes.ConstantesRest;
import notas.Common.modelo.Alumno;
import notas.Common.modelo.Usuario;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface ApiAlumno {

    @GET(ConstantesRest.PATH_ALUMNOS)
    Single<List<Alumno>> getAllAlumnos();
}
