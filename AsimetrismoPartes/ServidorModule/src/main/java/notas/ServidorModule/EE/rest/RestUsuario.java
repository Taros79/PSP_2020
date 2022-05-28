package notas.ServidorModule.EE.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modelo.Usuario;
import notas.ServidorModule.servicios.ServiciosUsuario;
import notas.ServidorModule.utils.Constantes;

@Path(ConstantesRest.PATH_USUARIOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuario {

    private final ServiciosUsuario serviciosUsuario;

    @Inject
    public RestUsuario(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }

    @RolesAllowed({Constantes.JEFE_DE_ESTUDIOS})
    @POST
    public Either<String, String> addUsuario(Usuario usuario) {
        return serviciosUsuario.addUsuario(usuario);
    }

}

/*
netstat -ano | findstr 8080

        Carlos, 17:24
        taskkill /F /PID pid_number.*/
