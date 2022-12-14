package notas.ClienteModule.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import notas.ClienteModule.dao.DaoAlumno;
import notas.CommonModule.modelo.Alumno;

import javax.inject.Inject;
import java.util.List;

public class ServiciosAlumno {

    private DaoAlumno daoAlumno;

    @Inject
    public ServiciosAlumno(DaoAlumno daoAlumno) {
        this.daoAlumno = daoAlumno;
    }

    public Single<Either<String, List<Alumno>>> getAllAlumnos() {
        return daoAlumno.getAllAlumnos();
    }
}
