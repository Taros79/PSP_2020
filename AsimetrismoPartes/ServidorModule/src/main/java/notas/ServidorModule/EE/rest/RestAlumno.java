package notas.ServidorModule.EE.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modelo.Alumno;
import notas.ServidorModule.servicios.ServiciosAlumno;
import notas.ServidorModule.utils.Constantes;

import java.util.List;

@Path(ConstantesRest.PATH_ALUMNOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({Constantes.JEFE_DE_ESTUDIOS, Constantes.PROFESOR})
public class RestAlumno {

    private final ServiciosAlumno serviciosAlumno;

    @Inject
    public RestAlumno(ServiciosAlumno serviciosAlumno) {
        this.serviciosAlumno = serviciosAlumno;
    }

    @GET
    public List<Alumno> getAllAlumnos() {
        return serviciosAlumno.getAllAlumnos();
    }

}

/*
netstat -ano | findstr 8080

        Carlos, 17:24
        taskkill /F /PID pid_number.*/
