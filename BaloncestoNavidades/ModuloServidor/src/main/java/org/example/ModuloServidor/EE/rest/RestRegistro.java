package org.example.ModuloServidor.EE.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;

@Path("/registro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestRegistro {

    private ServiciosUsuarios su;

    @Inject
    public RestRegistro(ServiciosUsuarios su) {
        this.su = su;
    }




    @POST
    public String addUsuarioRegistro(UsuarioRegistro u) {
        return su.addUsuario(u);
    }
}
