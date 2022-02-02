package org.example.ModuloServidor.EE.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.ModuloServidor.utils.Constantes;

@DeclareRoles({Constantes.ADMIN, Constantes.USER_min})
@ApplicationPath(Constantes.API)
public class JAXRSApplication extends Application {
}
