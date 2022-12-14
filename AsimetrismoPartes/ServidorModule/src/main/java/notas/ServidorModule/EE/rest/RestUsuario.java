package notas.ServidorModule.EE.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import notas.CommonModule.constantes.ConstantesRest;
import notas.CommonModule.modelo.Usuario;
import notas.ServidorModule.servicios.ServiciosUsuario;
import notas.ServidorModule.utils.Constantes;

import java.util.List;

@Path(ConstantesRest.PATH_USUARIOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuario {

    private final ServiciosUsuario serviciosUsuario;

    @Inject
    public RestUsuario(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }

    @RolesAllowed({Constantes.JEFE_DE_ESTUDIOS, Constantes.ADMIN})
    @POST
    public Either<String, String> addUsuario(Usuario usuario) {
        return serviciosUsuario.addUsuario(usuario);
    }

    @RolesAllowed({Constantes.ADMIN})
    @GET
    public List<Usuario> getAllUsuarios() {
        return serviciosUsuario.getAllUsuarios();
    }

}

/*
netstat -ano | findstr 8080

        Carlos, 17:24
        taskkill /F /PID pid_number.*/
