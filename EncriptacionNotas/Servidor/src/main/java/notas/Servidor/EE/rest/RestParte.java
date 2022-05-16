package notas.Servidor.EE.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.log4j.Log4j2;
import notas.Common.constantes.ConstantesRest;
import notas.Common.modelo.Parte;
import notas.Common.modelo.Usuario;
import notas.Servidor.servicios.ServiciosParte;

import java.util.List;

@Path(ConstantesRest.PATH_PARTES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestParte {

    private final ServiciosParte serviciosParte;

    @Inject
    public RestParte(ServiciosParte serviciosParte) {
        this.serviciosParte = serviciosParte;
    }

    @GET
    public List<Parte> getAllPartes() {
        return serviciosParte.getAllPartes();
    }

    @POST
    public String addParte(Parte parte) {
        return serviciosParte.addParte(parte);
    }
}

/*
netstat -ano | findstr 8080

        Carlos, 17:24
        taskkill /F /PID pid_number.*/
