package notas.ServidorModule.EE.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import notas.CommonModule.constantes.ConstantesRest;
import notas.ServidorModule.utils.Constantes;

@ApplicationPath(ConstantesRest.API)
@DeclareRoles({Constantes.JEFE_DE_ESTUDIOS, Constantes.PROFESOR, Constantes.PADRE, Constantes.ADMIN})
public class JAXRSApplication extends Application {

}
