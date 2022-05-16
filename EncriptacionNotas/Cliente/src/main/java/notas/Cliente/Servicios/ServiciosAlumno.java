package notas.Cliente.Servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import notas.Cliente.dao.DaoAlumno;
import notas.Cliente.dao.DaoUsuario;
import notas.Common.modelo.Alumno;
import notas.Common.modelo.Usuario;

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
