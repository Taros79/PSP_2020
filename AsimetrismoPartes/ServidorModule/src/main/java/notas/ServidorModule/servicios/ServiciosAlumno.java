package notas.ServidorModule.servicios;

import jakarta.inject.Inject;
import notas.CommonModule.modelo.Alumno;
import notas.ServidorModule.dao.DaoAlumno;

import java.util.List;

public class ServiciosAlumno {

    private final DaoAlumno daoAlumno;

    @Inject
    public ServiciosAlumno(DaoAlumno daoAlumno) {
        this.daoAlumno = daoAlumno;
    }

    public List<Alumno> getAllAlumnos() {
        return daoAlumno.getAllAlumnos();
    }
}
