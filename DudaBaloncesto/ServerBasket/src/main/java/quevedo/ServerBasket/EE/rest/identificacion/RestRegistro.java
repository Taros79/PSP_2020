package quevedo.ServerBasket.EE.rest.identificacion;

import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import quevedo.ServerBasket.dao.model.UserDatabase;
import quevedo.ServerBasket.services.PasswordHash;
import quevedo.ServerBasket.services.identificacion.MandarMail;
import quevedo.ServerBasket.services.identificacion.RegisterService;
import quevedo.ServerBasket.utils.CodeGenartor;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.ServerBasket.utils.constantes.CorreosStrings;
import quevedo.ServerBasket.utils.constantes.Mensajes;
import quevedo.ServerBasket.utils.constantes.MensajesErrores;
import quevedo.common.error.ApiError;
import quevedo.common.modelo.Mensaje;
import quevedo.common.modelo.User;
import quevedo.common.utils.PathRest;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Path(PathRest.REGISTRO_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestRegistro {


    private final MandarMail mandarMail;
    private final RegisterService registerService;
    private final PasswordHash passwordHash;


    @Inject
    public RestRegistro(MandarMail mandarMail, RegisterService registerService, PasswordHash passwordHash) {
        this.mandarMail = mandarMail;
        this.registerService = registerService;
        this.passwordHash = passwordHash;
    }


    @POST
    @RolesAllowed({ConstantesParametros.ADMIN_ROLE})
    @Path(PathRest.ADMIN_REGISTRO_PATH)
    public Response addAdmin(User user) {

        String randomCode = CodeGenartor.randomBytes();
        UserDatabase userDatabase = new UserDatabase(0, passwordHash.hash(user.getPassword()), user.getCorreo(), randomCode,
                LocalDateTime.now(ZoneId.of(ConstantesParametros.EUROPE_MADRID)), false, 1);
        Either<String, Boolean> result = registerService.addUser(userDatabase);

        Response response = null;

        if (result.isRight()) {
            try {
//
                mandarMail.generateAndSendEmail(user.getCorreo(),
                        CorreosStrings.ACTIVAR_CUENTA_HTML + randomCode + CorreosStrings.ACTIVAR_CUENTA_ENLACE
                        , CorreosStrings.SUBJECT_ACTIVACION);
                response = Response.status(Response.Status.CREATED)
                        .entity(new Mensaje(Mensajes.ACTIVE_LA_CUENTA_ATRAVES_DE_SU_CORREO))
                        .build();
            } catch (Exception e) {
                log.error(e.getMessage());
                response = Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiError(MensajesErrores.HA_HABIDO_UN_ERROR_AL_MANDAR_EL_MAIL_INTENTELO_MAS_TARDE))
                        .build();
            }
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;

    }


    @POST
    @PermitAll
    public Response addUser(User user) {

        String randomCode = CodeGenartor.randomBytes();
        UserDatabase userDatabase = new UserDatabase(0, passwordHash.hash(user.getPassword()), user.getCorreo(), randomCode,
                LocalDateTime.now(ZoneId.of(ConstantesParametros.EUROPE_MADRID)).plusMinutes(30), false, 2);
        Either<String, Boolean> result = registerService.addUser(userDatabase);

        Response response;

        if (result.isRight()) {
            try {
                String mensajeCorreo = CorreosStrings.ACTIVAR_CUENTA_HTML + randomCode + CorreosStrings.CORREO_HTML + user.getCorreo() + CorreosStrings.ACTIVAR_CUENTA_ENLACE;
                mandarMail.generateAndSendEmail(user.getCorreo(),
                        mensajeCorreo
                        , CorreosStrings.SUBJECT_ACTIVACION);
                response = Response.status(Response.Status.CREATED)
                        .entity(new Mensaje(Mensajes.ACTIVE_LA_CUENTA_ATRAVES_DE_SU_CORREO))
                        .build();
            } catch (Exception e) {
                log.error(e.getMessage());
                response = Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiError(MensajesErrores.HA_HABIDO_UN_ERROR_AL_MANDAR_EL_MAIL_INTENTELO_MAS_TARDE))
                        .build();
            }
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(result.getLeft()))
                    .build();
        }

        return response;

    }


}
