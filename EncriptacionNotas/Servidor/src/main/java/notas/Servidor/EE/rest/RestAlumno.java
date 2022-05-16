package notas.Servidor.EE.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import notas.Common.constantes.ConstantesRest;
import notas.Common.modelo.Alumno;
import notas.Common.modelo.Parte;
import notas.Servidor.servicios.ServiciosAlumno;
import notas.Servidor.servicios.ServiciosParte;
import notas.Servidor.utils.Constantes;

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
