package notas.Servidor.EE.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import notas.Common.constantes.ConstantesRest;
import notas.Servidor.utils.Constantes;

@ApplicationPath(ConstantesRest.API)
@DeclareRoles({Constantes.JEFE_DE_ESTUDIOS, Constantes.PROFESOR, Constantes.PADRE})
public class JAXRSApplication extends Application {

}
