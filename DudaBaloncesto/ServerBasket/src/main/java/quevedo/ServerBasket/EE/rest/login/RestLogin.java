package quevedo.ServerBasket.EE.rest.login;


import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import quevedo.ServerBasket.services.identificacion.LoginService;
import quevedo.ServerBasket.utils.constantes.MensajesErrores;
import quevedo.common.error.ApiError;
import quevedo.common.modelo.User;
import quevedo.common.modelo.UserLoged;
import quevedo.common.utils.PathRest;
import quevedo.common.utils.StringsCommon;

@Path(PathRest.LOGIN_REST)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestLogin {


    private final SecurityContext security;
    private final LoginService loginService;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public RestLogin(SecurityContext security, LoginService loginService, Pbkdf2PasswordHash passwordHash) {
        this.security = security;
        this.loginService = loginService;
        this.passwordHash = passwordHash;
    }

    @GET
    @PermitAll
    public Response login(@QueryParam(StringsCommon.CORREO) String correo,
                          @QueryParam(StringsCommon.PASSW) String passw) {
        Response response;
        Either<String, User> result = loginService.geUser(correo);
        if (result.isRight()) {
            if (passwordHash.verify(passw.toCharArray(),
                    result.get().getPassword())) {
                response = Response.status(Response.Status.OK)
                        .entity(new UserLoged(result.get().getCorreo(), result.get().getTipoUser()))
                        .build();
            } else {
//                Tratar contra incorrecta
                response = Response.status(Response.Status.NOT_FOUND)
                        .entity(new ApiError(MensajesErrores.USUARIO_O_CONTRASEÑA_INCORRECTOS))
                        .build();

            }

        } else {
            response = Response.status(Response.Status.NOT_FOUND).entity(new ApiError(result.getLeft())).build();
        }

        return response;

    }

}
