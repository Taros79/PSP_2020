package quevedo.ServerBasket.EE.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.common.utils.PathRest;

@ApplicationPath(PathRest.API)
@DeclareRoles({ConstantesParametros.ADMIN_ROLE, ConstantesParametros.USER})
public class ServidorWebApplication extends Application {
}
