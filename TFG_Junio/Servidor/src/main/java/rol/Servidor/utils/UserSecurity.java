package rol.Servidor.utils;

import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.extern.log4j.Log4j2;
import rol.Common.modelo.Usuario;
import rol.Servidor.servicios.ServiciosUsuario;

@Log4j2
public class UserSecurity {

    private final ServiciosUsuario serviciosUsuario;
    private final SecurityContext securityContext;

    @Inject
    public UserSecurity(ServiciosUsuario serviciosUsuario, SecurityContext securityContext) {
        this.serviciosUsuario = serviciosUsuario;
        this.securityContext = securityContext;
    }

    public Usuario getUserSession() {
        return serviciosUsuario.getUsuarioByName(this.securityContext.getCallerPrincipal().getName());
    }

}
