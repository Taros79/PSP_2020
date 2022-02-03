package quevedo.ServerBasket.EE.rest.identificacion;


import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import quevedo.ServerBasket.services.identificacion.CambioPasswService;
import quevedo.ServerBasket.services.identificacion.MandarMail;
import quevedo.ServerBasket.utils.CodeGenartor;
import quevedo.ServerBasket.utils.constantes.CorreosStrings;
import quevedo.ServerBasket.utils.constantes.MensajesErrores;
import quevedo.common.error.ApiError;
import quevedo.common.modelo.Mensaje;
import quevedo.common.utils.PathRest;
import quevedo.common.utils.StringsCommon;

@Path(PathRest.CAMBIO_PASSW)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestCambioPassw {

    private final MandarMail mandarMail;
    private final CambioPasswService cambioPasswService;

    @Inject
    public RestCambioPassw(MandarMail mandarMail, CambioPasswService cambioPasswService) {
        this.mandarMail = mandarMail;
        this.cambioPasswService = cambioPasswService;
    }

    @GET
    @PermitAll
    public Response cambioPassw(@QueryParam(StringsCommon.CORREO) String correo) {

        Response response = null;

        Either<String, Boolean> compruebaCorreo = cambioPasswService.confirmaUser(correo);
        Either<String, Boolean> addTimeAndCOdeResult;


        if (compruebaCorreo.isRight()) {
            String randomCode = CodeGenartor.randomBytes();
            addTimeAndCOdeResult = cambioPasswService.addCodAndTime(randomCode, correo);
            if (addTimeAndCOdeResult.isRight()) {
                try {

                    mandarMail.generateAndSendEmail(correo,
                            CorreosStrings.HTML_CAMBIO_PASSW + randomCode + CorreosStrings.ENLACE_CAMBIO_PASSW
                            , CorreosStrings.CAMBIO_CONTRASEÑA_SUBJECT);

                    response = Response.status(Response.Status.CREATED)
                            .entity(new Mensaje(MensajesErrores.CONFIRME_EL_CORREO_PARA_CAMBIAR_LA_CONTRASEÑA))
                            .build();
                } catch (Exception e) {
                    log.error(e.getMessage());
                    response = Response.status(Response.Status.CREATED)
                            .entity(new ApiError(MensajesErrores.FALLO_AL_MANDAR_EL_CORREO))
                            .build();
                }
            } else {
                response = Response.status(Response.Status.CREATED)
                        .entity(new ApiError(addTimeAndCOdeResult.getLeft()))
                        .build();
            }


        } else {
            response = Response.status(Response.Status.CREATED)
                    .entity(new ApiError(compruebaCorreo.getLeft()))
                    .build();
        }


        return response;

    }


}
