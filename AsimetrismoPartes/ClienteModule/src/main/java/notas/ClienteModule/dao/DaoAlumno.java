package notas.ClienteModule.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import notas.ClienteModule.dao.retrofit.ApiAlumno;
import notas.CommonModule.modelo.Alumno;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoAlumno extends DaoGenerics {


    private final ApiAlumno apiAlumno;


    @Inject
    public DaoAlumno(Gson gson, ApiAlumno apiAlumno) {
        super(gson);
        this.apiAlumno = apiAlumno;
    }

    public Single<Either<String, List<Alumno>>> getAllAlumnos() {
        return safeSingleApicall(apiAlumno.getAllAlumnos());
    }
}
