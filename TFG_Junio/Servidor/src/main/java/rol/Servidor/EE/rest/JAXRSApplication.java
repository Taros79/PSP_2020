package rol.Servidor.EE.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import rol.Common.constantes.ConstantesRest;
import rol.Servidor.utils.Constantes;

@ApplicationPath(ConstantesRest.API)
@DeclareRoles({Constantes.USUARIO,Constantes.JUGADOR,Constantes.ADMIN})
public class JAXRSApplication extends Application {

}
