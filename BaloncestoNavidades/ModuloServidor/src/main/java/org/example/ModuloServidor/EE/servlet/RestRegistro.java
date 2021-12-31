/*
package org.example.ModuloServidor.EE.servlet;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.ModuloServidor.servicios.ServiciosUsuarios;

import java.time.LocalDateTime;

@Path("/registro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestRegistro {

    private ServiciosUsuarios su;

    @Inject
    public RestRegistro(ServiciosUsuarios su) {
        this.su = su;
    }

    public RestRegistro() {
    }

*/
/*



        usuarios (id,correo,h(pass),codActivacion,codCambioPass,siActivo,fechaLimite,idTipoUsuario)
        tipoUsuario(id,String)

        equipos(id, nombre)
        jornadas(id, fecha)
        partidos(idJornada,idEquipo,idEquipo,resultado) "120-56")




     *//*



    @POST
    public Response addUsuario(Usuario user){

        Usuario s = su.addUser(user);

        //hashear la pass

        //array seguro




        //mandar mail


        if (s !=null)
            return Response.status(Response.Status.CREATED).entity(s).build();
        else
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiError.builder()
                            .message("usuario no añadido")
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
    }


    @PUT
    @Path("cambiarPass")
    public Response actualizarContraseñaUsuario(Usuario u) {


        // generas codigoCambioPass nuevo.

        // actualziar codigoCambioPass y fecha limite



        //main mail para cambiode contraseña


        return null;
    }

    @PUT
    public Response actualizarUsuario(Usuario user) {


        return null;
    }

}
*/
